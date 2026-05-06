package com.likerhood.design;

import com.alibaba.fastjson.JSON;
import com.likerhood.design.mq.CreateAccout;
import com.likerhood.design.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
MQ 消息队列 → 发送 JSON 字符串
        → onMessage 接收
        → JSON 反序列化为对象
        → 取出字段处理业务

 */
public class CreateAccountMqService {
    private Logger logger = LoggerFactory.getLogger(CreateAccountMqService.class);

    public void onMessage(String message) {

        CreateAccout mq = JSON.parseObject(message, CreateAccout.class);

        mq.getNumber();
        mq.getAccountDate();

        // ... 处理自己的业务，比如根据用户编号判断是否发放优惠券
        logger.info("开户服务：分析用户是否要发放优惠券");
    }


}
