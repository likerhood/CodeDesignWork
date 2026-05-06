package com.likerhood.design;

import com.alibaba.fastjson.JSON;
import com.likerhood.design.mq.CreateAccout;
import com.likerhood.design.mq.OrderMq;
import com.likerhood.design.mq.POPOrderDelivered;
import org.junit.Test;

import java.util.Date;

public class ApiTest {

    @Test
    public void test_create_account() {
        // 模拟收到"开户"MQ消息（JSON字符串）
        CreateAccout account = new CreateAccout();
        account.setNumber("10001");
        account.setAddress("北京");
        account.setAccountDate(new Date());
        account.setDesc("新用户开户");

        // 消费消息
        CreateAccountMqService service = new CreateAccountMqService();
        service.onMessage(JSON.toJSONString(account));
    }

    @Test
    public void test_order() {
        // 模拟收到"自营下单"MQ消息
        OrderMq orderMq = new OrderMq();
        orderMq.setUid("10001");
        orderMq.setOrderId("ORDER_001");
        orderMq.setCreateOrderTime(new Date());

        OrderMqService service = new OrderMqService();
        service.onMessage(JSON.toJSONString(orderMq));
    }

    @Test
    public void test_pop_order_delivered() {
        // 模拟收到"POP订单妥投"MQ消息
        POPOrderDelivered delivered = new POPOrderDelivered();
        delivered.setuId("10001");
        delivered.setOrderId("ORDER_002");
        delivered.setOrderTime(new Date());

        POPOrderDeliveredService service = new POPOrderDeliveredService();
        service.onMessage(JSON.toJSONString(delivered));
    }




}