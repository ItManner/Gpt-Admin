package com.ruoyi.system.service;

import com.jeequan.jeepay.exception.JeepayException;
import com.ruoyi.system.domain.GptBalanceOrder;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Lan HuaZeng
 * @Date: 2023/5/19 14:27
 */
public interface JeePayService {

    /**
     * 统一生成订单接口,返回二维码
     *
     * @param orderInfoDto
     * @return
     * @throws JeepayException
     */
    String scanPay(GptBalanceOrder orderInfoDto) throws JeepayException;

    String tradeNotify(HttpServletRequest req);
}
