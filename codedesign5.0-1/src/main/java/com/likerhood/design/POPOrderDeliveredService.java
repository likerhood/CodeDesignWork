package com.likerhood.design;

import com.alibaba.fastjson.JSON;
import com.likerhood.design.mq.POPOrderDelivered;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// "POP订单妥投"MQ消息的处理
public class POPOrderDeliveredService {

    private Logger logger = LoggerFactory.getLogger(POPOrderDeliveredService.class);

    public void onMessage(String message) {

        POPOrderDelivered mq = JSON.parseObject(message, POPOrderDelivered.class);

        mq.getuId();
        mq.getOrderId();
        mq.getOrderTime();

        // ... 处理自己的业务
        logger.info("POP 订单妥投消息 — POP 商家（第三方入驻商家）的mq消息处理");
    }



}
