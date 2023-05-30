package com.ruoyi.web.controller.pay;

import com.jeequan.jeepay.exception.JeepayException;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.system.domain.GptOrder;
import com.ruoyi.system.domain.GptPackage;
import com.ruoyi.system.mapper.GptOrderMapper;
import com.ruoyi.system.mapper.GptPackageMapper;
import com.ruoyi.system.service.JeePayService;
import com.ruoyi.web.domain.Pay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author:xianyu
 * @createDate:2022/8/5
 * @description:
 */
@Api(tags = "支付功能")
@RestController()
public class JeePayController {

    private final JeePayService jeePayService;
    @Autowired
    private GptOrderMapper gptOrderMapper;
    @Autowired
    private GptPackageMapper gptPackageMapper;

    public JeePayController(JeePayService jeePayService) {
        this.jeePayService = jeePayService;
    }

    /**
     * 扫码下单
     */
    @PostMapping("/scanPay")
    @Anonymous
    @ApiOperation(value = "发起支付")
    public AjaxResult scanPay(@RequestBody Pay pay) throws JeepayException {
        //订单支付
        GptOrder gptOrder = new GptOrder();
        if (!ObjectUtils.isEmpty(pay.getOrderId())) {
            gptOrder = gptOrderMapper.selectGptOrderById(Long.valueOf(pay.getOrderId()));
            if (ObjectUtils.isEmpty(gptOrder))
                return AjaxResult.error("订单不存在，无法创建支付");
        } else {
            //如果订单ID为空就新建订单
            GptPackage gptPackage = gptPackageMapper.selectGptPackageById(Long.valueOf(pay.getPackageId()));
            if (ObjectUtils.isEmpty(gptOrder))
                return AjaxResult.error("套餐不存在，无法创建支付");
            gptOrder.setPayStatus("0");
            gptOrder.setAmount(gptPackage.getAmount());
            gptOrder.setOrderCode("Gpt_" + UUID.randomUUID());
            gptOrder.setGoodsTitle(gptPackage.getName());
            gptOrder.setPackageId(gptPackage.getId());
            gptOrder.setUserId(SecurityUtils.getUserId());
            gptOrder.setCreateTime(new Date());
        }
        if (gptOrderMapper.insertGptOrder(gptOrder) > 0) {
            return AjaxResult.success(jeePayService.scanPay(gptOrder));
        }
        return AjaxResult.error("创建失败");
    }

    @ApiOperation("支付回调")
    @Anonymous
    @PostMapping("/tradeNotify")
    public String tradeNotify(HttpServletRequest req) {
        return jeePayService.tradeNotify(req);
    }
}
