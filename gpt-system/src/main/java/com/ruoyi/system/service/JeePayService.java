package com.ruoyi.system.service;

import com.jeequan.jeepay.exception.JeepayException;
import com.ruoyi.system.domain.GptOrder;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Lan HuaZeng
 * @Date: 2023/5/19 14:27
 */
public interface JeePayService {

    /**
     * 统一生成订单接口,返回二维码
     *
     * @return
     * @throws JeepayException
     */
    String scanPay(GptOrder gptOrder) throws JeepayException;

    String tradeNotify(HttpServletRequest req);
}
