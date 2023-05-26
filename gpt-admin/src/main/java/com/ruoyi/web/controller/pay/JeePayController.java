package com.ruoyi.web.controller.pay;

import com.jeequan.jeepay.exception.JeepayException;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.GptOrder;
import com.ruoyi.system.service.JeePayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:xianyu
 * @createDate:2022/8/5
 * @description:
 */
@Api(tags = "计全聚合支付")
@RestController()
public class JeePayController {

    private final JeePayService jeePayService;

    public JeePayController(JeePayService jeePayService) {
        this.jeePayService = jeePayService;
    }

    /**
     * 扫码下单
     */
    @GetMapping("/scanPay")
    @Anonymous
    public AjaxResult scanPay(@RequestParam String orderId) throws JeepayException {
        return AjaxResult.success(jeePayService.scanPay(orderId));
    }

    @ApiOperation("支付回调")
    @Anonymous
    @PostMapping("/tradeNotify")
    public AjaxResult tradeNotify(HttpServletRequest req) {
        return AjaxResult.success(jeePayService.tradeNotify(req),null);
    }
}
