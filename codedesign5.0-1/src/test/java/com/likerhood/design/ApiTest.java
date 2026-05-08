package com.likerhood.design;

import com.alibaba.fastjson.JSON;
import com.likerhood.design.mq.CreateAccout;
import com.likerhood.design.mq.OrderMq;
import com.likerhood.design.mq.POPOrderDelivered;
import com.likerhood.design.service.OrderService;
import com.likerhood.design.service.POPOrderService;
import org.junit.Test;

import java.util.Date;

public class ApiTest {


    @Test
    public void test_no_pattern_create_account() {
        // 模拟开户 MQ 到达
        CreateAccout mq = new CreateAccout();
        mq.setNumber("100001");
        mq.setAddress("北京市");
        mq.setAccountDate(new Date());
        mq.setDesc("新用户在校开户");

        // ❌ 每个 MQService 只是解析消息，根本没有判断首单、发券的逻辑
        // 因为三种 MQ 字段不同、service 不同，开发者不知道怎么统一处理
        // 于是选择最简单的办法：每个 handler 各自为政，重复写
        CreateAccountMqService service = new CreateAccountMqService();
        service.onMessage(JSON.toJSONString(mq));

        // ❌ 如果要加发券逻辑，只能在这里硬写：
        //    手动知道开户用 number 字段取 userId
        String userId = mq.getNumber();
        //    手动知道开户场景用 OrderService 判断（还是 POPOrderService？不清晰）
        OrderService orderService = new OrderService();
        boolean isFirst = orderService.queryUserOrderCount(userId) <= 1;
        if (isFirst) {
            System.out.println("用户[" + userId + "]首单，发开户返利券");
        }
        // ❌ 这段逻辑马上要在下面两个测试里再抄两遍
    }

    @Test
    public void test_no_pattern_order_mq() {
        // 模拟自营下单 MQ 到达
        OrderMq mq = new OrderMq();
        mq.setUid("100001");
        mq.setOrderId("ORDER_20240101_001");
        mq.setCreateOrderTime(new Date());

        OrderMqService service = new OrderMqService();
        service.onMessage(JSON.toJSONString(mq));

        // ❌ 自营订单：userId 字段叫 uid（不是 number 了）
        String userId = mq.getUid();
        //    自营订单还是用 OrderService
        OrderService orderService = new OrderService();
        boolean isFirst = orderService.queryUserOrderCount(userId) <= 1;
        if (isFirst) {
            System.out.println("用户[" + userId + "]首单，发自营首单返利券");
        }
        // ❌ 和上面几乎一样，复制粘贴改了个字段名
    }

    @Test
    public void test_no_pattern_pop_order_delivered() {
        // 模拟 POP 订单妥投 MQ 到达
        POPOrderDelivered mq = new POPOrderDelivered();
        mq.setuId("100001");
        mq.setOrderId("POP_ORDER_001");
        mq.setOrderTime(new Date());

        POPOrderDeliveredService service = new POPOrderDeliveredService();
        service.onMessage(JSON.toJSONString(mq));

        // ❌ POP 订单：userId 字段叫 uId（大小写又变了）
        String userId = mq.getuId();
        // ❌ POP 订单改用 POPOrderService，方法名也不同
        POPOrderService popOrderService = new POPOrderService();
        boolean isFirst = popOrderService.isFirstOrder(userId);
        if (isFirst) {
            System.out.println("用户[" + userId + "]首单，发POP首单返利券");
        }
        // ❌ 调用方必须记住：POP 要用 popOrderService，自营要用 orderService
        //    这些知识散落在每个 handler 里，换人维护就是噩梦
    }




}