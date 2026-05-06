package com.likerhood.design.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// "POP订单妥投"MQ消息
public class POPOrderService {

    private Logger logger = LoggerFactory.getLogger(POPOrderService.class);

    public boolean isFirstOrder(String uId) {
        logger.info("POP商家，查询用户的订单是否为首单：{}", uId);
        return true;
    }
}
