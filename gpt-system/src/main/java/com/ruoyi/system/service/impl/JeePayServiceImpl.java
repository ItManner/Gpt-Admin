package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.EncodeHintType;
import com.jeequan.jeepay.Jeepay;
import com.jeequan.jeepay.JeepayClient;
import com.jeequan.jeepay.exception.JeepayException;
import com.jeequan.jeepay.model.PayOrderCreateReqModel;
import com.jeequan.jeepay.model.PayOrderCreateResModel;
import com.jeequan.jeepay.request.PayOrderCreateRequest;
import com.jeequan.jeepay.response.PayOrderCreateResponse;
import com.jeequan.jeepay.util.JeepayKit;
import com.ruoyi.common.qrcode.QRBtf;
import com.ruoyi.common.qrcode.renderer.Renderer;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.GptOrder;
import com.ruoyi.system.domain.GptPackage;
import com.ruoyi.system.domain.GptUserPackage;
import com.ruoyi.system.mapper.GptOrderMapper;
import com.ruoyi.system.mapper.GptPackageMapper;
import com.ruoyi.system.mapper.GptUserPackageMapper;
import com.ruoyi.system.service.JeePayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * @author:xianyu
 * @createDate:2022/8/5
 * @description:
 */
@Slf4j
@Service
public class JeePayServiceImpl implements JeePayService {

    @Autowired
    private JeepayClient jeepayClient;

    @Autowired
    private Environment config;

    @Autowired
    private GptOrderMapper gptOrderMapper;

    @Autowired
    private GptUserPackageMapper gptUserPackageMapper;

    @Autowired
    private GptPackageMapper gptPackageMapper;

    private final static HashMap<EncodeHintType, Object> encodeHint = new HashMap<>();


    @Override
    @Transactional
    public JSONObject scanPay(GptOrder gptOrder) {
        // 构建请求数据
        PayOrderCreateRequest request = new PayOrderCreateRequest();
        PayOrderCreateReqModel model = new PayOrderCreateReqModel();
        // 商户号
        model.setMchNo(Jeepay.mchNo);
        // 应用ID
        model.setAppId(Jeepay.appId);
        // 商户订单号
        model.setMchOrderNo(gptOrder.getOrderCode());
        // 支付方式
        model.setWayCode("QR_CASHIER");
        // 金额，单位分
        long amount = gptOrder.getAmount().multiply(new BigDecimal(100)).longValue();
        model.setAmount(amount);
        // 币种，目前只支持cny
        model.setCurrency("CNY");
        // 发起支付请求客户端的IP地址
        model.setClientIp(config.getProperty("ip-address"));
        // 商品标题
        model.setSubject(gptOrder.getGoodsTitle());
        // 商品描述
        model.setBody("人工智能");
        // 异步通知地址
        model.setNotifyUrl(config.getProperty("notify"));
        // 前端跳转地址
        model.setReturnUrl("");
        // 渠道扩展参数
//        model.setChannelExtra(channelExtra(JeePayConstants.QR_CASHIER));
        // 商户扩展参数,回调时原样返回
        model.setExtParam("");
        request.setBizModel(model);
        log.info("jeepay下单参数处理完毕,参数:[{}]", JSON.toJSONString(request));
        String result = "failure";
        JSONObject res = new JSONObject();
        try {
            PayOrderCreateResponse response = jeepayClient.execute(request);
            // 下单成功
            if (response.isSuccess(Jeepay.apiKey)) {
                PayOrderCreateResModel payOrderCreateResModel = response.get();
                result = "success";
                String qrBase64Str = handleQrcode(payOrderCreateResModel.getPayData());
                res.put("payImg", qrBase64Str);
                res.put("payStatus", result);
            } else {
                log.info("下单失败：{}", gptOrder.getOrderCode());
                res.put("payStatus", result);
            }
        } catch (JeepayException e) {
            log.info(e.getMessage());
        }
        return res;
    }


    @Override
    @Transactional
    public String tradeNotify(HttpServletRequest req) {
        String result = "failure";
        try {
            Map<String, Object> map = getParamsMap(req);
            //获取私钥
            String apikey = config.getProperty("api-key");
            //验签
            if (chackSgin(map, apikey)) {
                return result;
            }
            //支付成功 修改订单为已支付
            GptOrder gptOrder = gptOrderMapper.selectGptOrderByOrderCode(map.get("mchOrderNo").toString());
            gptOrder.setPayStatus("1");
            if (gptOrderMapper.updateGptOrder(gptOrder) > 0) {
                //返回成功
                GptPackage gptPackage = gptPackageMapper.selectGptPackageById(gptOrder.getPackageId());
                GptUserPackage gptUserPackage = new GptUserPackage();
                gptUserPackage.setPackageId(gptPackage.getId());
                //获取当天日期
                LocalDate today = LocalDate.now();
                //获取套餐对应的天数日期
                LocalDate afterFiveDays = today.plusDays(gptPackage.getValidTime());
                gptUserPackage.setExpireTime(Date.from(afterFiveDays.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                gptUserPackage.setUserId(SecurityUtils.getLoginUser().getUserId());
                gptUserPackage.setRemainingCount(gptPackage.getLimitCount());
                gptUserPackage.setIsExpire("1");
                if (gptUserPackageMapper.insertGptUserPackage(gptUserPackage) == 0) return result;;
                result = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 回调验签
     *
     * @param
     * @param map
     * @param apikey
     * @return
     */
    private Boolean chackSgin(Map<String, Object> map, String apikey) {

        Object sign = map.remove("sign");
        String reSign = JeepayKit.getSign(map, apikey);
        log.info("调用SDK加签,返回参数:[{}]", reSign);
        if (!Objects.equals(reSign, sign)) {
            log.info("支付成功,异步通知验签失败!");
            return true;
        }
        log.info("支付成功,异步通知验签成功!");
        //1.验证mchOrderNo 是否为商家系统中创建的订单号
        log.info("支付成功回调参数：{}",map);
        GptOrder orderInfos = gptOrderMapper.selectGptOrderByOrderCode(map.get("mchOrderNo").toString());
        log.info("查询订单,[{}]", JSON.toJSONString(orderInfos));
        if (ObjectUtils.isEmpty(orderInfos)) {
            log.info("支付成功,回调通知,mchOrderNo不是本系统生成的订单号!!");
            return true;
        }
        //2.判断 amountt 是否确实为该订单的实际金额
        //订单金额单位转换为分
        BigDecimal reduce = orderInfos.getAmount().multiply(new BigDecimal(100));
        BigDecimal amount = new BigDecimal(map.get("amount").toString());
        if (reduce.compareTo(amount) != 0) {
            log.info("支付成功,回调通知,订单金额与实际金额不符!!");
            return true;
        }
        return false;
    }

    private Map<String, Object> getParamsMap(HttpServletRequest req) {
        Map<String, String[]> requestMap = req.getParameterMap();
        Map<String, Object> paramsMap = new HashMap<>();
        requestMap.forEach((key, values) -> {
            String strs = "";
            for (String value : values) {
                strs = strs + value;
            }
            paramsMap.put(key, strs);
        });
        return paramsMap;
    }


    public String handleQrcode(String url) {
        try {
            Renderer renderer = Renderer.line().adjust()
                    .end();

            QRBtf qrBtf = new QRBtf(renderer);
            BufferedImage image = qrBtf.encode(url, encodeHint);

            // 将BufferedImage对象转换成ByteArrayOutputStream对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            // 将ByteArrayOutputStream对象转换成字节数组
            byte[] imageBytes = baos.toByteArray();

            // 使用Base64类将字节数组转换成base64字符串
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
