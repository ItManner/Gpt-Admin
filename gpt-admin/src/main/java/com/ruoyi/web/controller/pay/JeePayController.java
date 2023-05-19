package com.ruoyi.web.controller.pay;

import com.jeequan.jeepay.exception.JeepayException;
import com.ruoyi.system.domain.GptBalanceOrder;
import com.ruoyi.system.service.JeePayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author:xianyu
 * @createDate:2022/8/5
 * @description:
 */
@Api(tags = "计全聚合支付")
@RestController()
public class JeePayController {

    @Autowired
    private JeePayService jeePayService;

    /**
     * 扫码下单
     *
     * @param orderInfoDto
     * @return
     * @throws JeepayException
     */
    @PostMapping("/scanPay")
    public String scanPay(@RequestBody GptBalanceOrder orderInfoDto) throws JeepayException {
        return jeePayService.scanPay(orderInfoDto);
    }

    @ApiOperation("支付回调")
    @PostMapping("/tradeNotify")
    public String tradeNotify(HttpServletRequest req) {
        return jeePayService.tradeNotify(req);
    }
}
