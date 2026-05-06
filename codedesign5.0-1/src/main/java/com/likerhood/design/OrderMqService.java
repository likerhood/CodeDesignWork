package com.likerhood.design;

import com.alibaba.fastjson.JSON;
import com.likerhood.design.mq.OrderMq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderMqService {

    private Logger logger = LoggerFactory.getLogger(OrderMqService.class);
    public void onMessage(String message) {

        OrderMq mq = JSON.parseObject(message, OrderMq.class);

        mq.getUid();
        mq.getOrderId();
        mq.getCreateOrderTime();

        // ... 处理自己的业务
        logger.info("处理用户下单自营商家的业务，比如购买京东的自营的oppo旗舰店手机");
    }
}
