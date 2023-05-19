package com.ruoyi.web.core.config;

import com.jeequan.jeepay.Jeepay;
import com.jeequan.jeepay.JeepayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author:xianyu
 * @createDate:2022/8/5
 * @description:
 */
@Configuration
@PropertySource("classpath:jee-pay.properties")
public class JeePayClientConfig {

    @Autowired
    private Environment config;

    @Bean
    public JeepayClient jeePayConfig(){

        Jeepay.setApiBase(config.getProperty("api-base"));
        Jeepay.apiKey = config.getProperty("api-key");
        Jeepay.mchNo = config.getProperty("mch-no");
        Jeepay.appId = config.getProperty("app-id");

        JeepayClient jeepayClient = JeepayClient.getInstance(Jeepay.appId, Jeepay.apiKey, Jeepay.getApiBase());

        return jeepayClient;
    }
}
