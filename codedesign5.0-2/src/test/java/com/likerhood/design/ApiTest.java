package com.likerhood.design;

import com.likerhood.design.impl.InsideOrderServiceImpl;
import com.likerhood.design.impl.POPOrderAdapterServiceImpl;
import com.likerhood.design.mq.CreateAccout;
import com.likerhood.design.mq.OrderMq;
import com.likerhood.design.mq.POPOrderDelivered;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ApiTest {


    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    // =====================================================================
    // 测试一：MQAdapter — 三种 MQ 字段适配成统一 RebateInfo
    // =====================================================================

    @Test
    public void test_MQAdapter_all_three() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // ---------- 开户 MQ ----------
        CreateAccout accountMq = new CreateAccout();
        accountMq.setNumber("100001");
        accountMq.setAddress("北京市");
        accountMq.setAccountDate(sdf.parse("2024-01-01 10:00:00"));
        accountMq.setDesc("在校开户");

        HashMap<String, String> accountLink = new HashMap<>();
        accountLink.put("userId",  "number");      // RebateInfo.userId ← create_account.number
        accountLink.put("bizId",   "number");
        accountLink.put("bizTime", "accountDate");
        accountLink.put("desc",    "desc");

        RebateInfo rebate01 = MQAdapter.filter(accountMq.toString(), accountLink);
        logger.info("开户MQ适配前: {}", accountMq);
        logger.info("开户MQ适配后: userId={}, bizTime={}", rebate01.getUserId(), rebate01.getBizTime());

        // ---------- 自营下单 MQ ----------
        OrderMq orderMq = new OrderMq();
        orderMq.setUid("100001");
        orderMq.setOrderId("ORDER_001");
        orderMq.setCreateOrderTime(sdf.parse("2024-01-02 15:00:00"));

        HashMap<String, String> orderLink = new HashMap<>();
        orderLink.put("userId",  "uid");            // RebateInfo.userId ← OrderMq.uid
        orderLink.put("bizId",   "orderId");
        orderLink.put("bizTime", "createOrderTime");

        RebateInfo rebate02 = MQAdapter.filter(orderMq.toString(), orderLink);
        logger.info("自营订单MQ适配后: userId={}, bizId={}", rebate02.getUserId(), rebate02.getBizId());

        // ---------- POP 妥投 MQ ----------
        POPOrderDelivered popMq = new POPOrderDelivered();
        popMq.setuId("100001");
        popMq.setOrderId("POP_ORDER_001");
        popMq.setOrderTime(sdf.parse("2024-01-03 18:00:00"));

        HashMap<String, String> popLink = new HashMap<>();
        popLink.put("userId",  "uId");              // RebateInfo.userId ← POPOrderDelivered.uId
        popLink.put("bizId",   "orderId");
        popLink.put("bizTime", "orderTime");

        RebateInfo rebate03 = MQAdapter.filter(popMq.toString(), popLink);
        logger.info("POP订单MQ适配后: userId={}, bizId={}", rebate03.getUserId(), rebate03.getBizId());

        // ✅ 三种 MQ，三套字段，最终都变成统一的 RebateInfo
        // ✅ 调用方只认识 RebateInfo，不需要知道原始字段叫 number/uid/uId
    }

    // =====================================================================
    // 测试二：OrderAdapterService — 两种 Service 适配成统一 isFirst()
    // =====================================================================

    @Test
    public void test_OrderAdapterService_unified() {
        String userId = "100001";

        // ✅ 自营订单判断首单：用统一接口，不需要知道底层调的是 queryUserOrderCount()
        OrderAdapterService insideAdapter = new InsideOrderServiceImpl();
        boolean insideIsFirst = insideAdapter.isFirst(userId);
        logger.info("自营首单判断: userId={}, isFirst={}", userId, insideIsFirst);

        // ✅ POP 订单判断首单：用统一接口，不需要知道底层调的是 isFirstOrder()
        OrderAdapterService popAdapter = new POPOrderAdapterServiceImpl();
        boolean popIsFirst = popAdapter.isFirst(userId);
        logger.info("POP首单判断: userId={}, isFirst={}", userId, popIsFirst);

        // ✅ 对比面条代码：调用方不再需要知道谁用 OrderService、谁用 POPOrderService
        // ✅ 两个 adapter 对外接口完全一样：isFirst(userId)
    }

    // =====================================================================
    // 测试三：完整业务流程 — MQAdapter + OrderAdapterService 联动
    // =====================================================================

    @Test
    public void test_full_rebate_flow() throws Exception {
        // 模拟三种 MQ 消息依次到达，走完整的"判断首单→发券"流程

        // --- 场景1：用户开户，判断是否发开户返利券 ---
        CreateAccout accountMq = new CreateAccout();
        accountMq.setNumber("100001");
        accountMq.setAccountDate(new Date());
        accountMq.setDesc("新用户开户");

        HashMap<String, String> accountLink = new HashMap<>();
        accountLink.put("userId",  "number");
        accountLink.put("bizId",   "number");
        accountLink.put("bizTime", "accountDate");
        accountLink.put("desc",    "desc");

        RebateInfo rebate = MQAdapter.filter(accountMq.toString(), accountLink);
        // 开户场景 → 用自营首单判断
        OrderAdapterService adapter = new InsideOrderServiceImpl();
        sendRebateCoupon(rebate, adapter, "开户返利券");

        // --- 场景2：自营下单，判断是否发首单券 ---
        OrderMq orderMq = new OrderMq();
        orderMq.setUid("100002");
        orderMq.setOrderId("ORDER_002");
        orderMq.setCreateOrderTime(new Date());

        HashMap<String, String> orderLink = new HashMap<>();
        orderLink.put("userId",  "uid");
        orderLink.put("bizId",   "orderId");
        orderLink.put("bizTime", "createOrderTime");

        rebate = MQAdapter.filter(orderMq.toString(), orderLink);
        sendRebateCoupon(rebate, new InsideOrderServiceImpl(), "自营首单券");

        // --- 场景3：POP 妥投，判断是否发首单券 ---
        POPOrderDelivered popMq = new POPOrderDelivered();
        popMq.setuId("100003");
        popMq.setOrderId("POP_003");
        popMq.setOrderTime(new Date());

        HashMap<String, String> popLink = new HashMap<>();
        popLink.put("userId",  "uId");
        popLink.put("bizId",   "orderId");
        popLink.put("bizTime", "orderTime");

        rebate = MQAdapter.filter(popMq.toString(), popLink);
        sendRebateCoupon(rebate, new POPOrderAdapterServiceImpl(), "POP首单券");

        // ✅ 三种场景，发券逻辑只写了一次：sendRebateCoupon()
        // ✅ 每种差异（字段/service）只在各自的 link 和 adapter 里体现
    }

    /**
     * ✅ 这是适配器模式的最大价值所在：
     * 发券逻辑只写一次，接收统一的 RebateInfo 和 OrderAdapterService
     * 无论来自哪种 MQ、哪种 Service，这里永远不需要修改
     */
    private void sendRebateCoupon(RebateInfo rebateInfo, OrderAdapterService adapter, String couponType) {
        boolean isFirst = adapter.isFirst(rebateInfo.getUserId());
        if (isFirst) {
            logger.info("用户[{}]是首单，发放[{}]，bizId={}",
                    rebateInfo.getUserId(), couponType, rebateInfo.getBizId());
        } else {
            logger.info("用户[{}]非首单，不发券", rebateInfo.getUserId());
        }
    }

//    public static void main(String[] args) throws Exception {
//        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date parse = s.parse("2020-06-01 23:20:16");
//
//        CreateAccout create_account = new CreateAccout();
//        create_account.setNumber("100001");
//        create_account.setAddress("河北省.廊坊市.广阳区.大学里职业技术学院");
//        create_account.setAccountDate(parse);
//        create_account.setDesc("在校开户");
//
//        HashMap<String, String> link01 = new HashMap<String, String>();
//        link01.put("userId", "number");
//        link01.put("bizId", "number");
//        link01.put("bizTime", "accountDate");
//        link01.put("desc", "desc");
//
//        RebateInfo rebateInfo01 = MQAdapter.filter(create_account.toString(), link01);
//        System.out.println("mq.create_account(适配前)" + create_account.toString());
//        System.out.println("mq.create_account(适配后)" + JSON.toJSONString(rebateInfo01));
//
//    }

}