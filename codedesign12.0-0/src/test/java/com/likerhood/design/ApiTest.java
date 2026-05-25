package com.likerhood.design;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApiTest {

    private final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Before
    public void setUp() throws Exception {
        clearAuthServiceState();
    }

    @Test
    public void test_AuthController_corePromotionDate() throws Exception {
        AuthController authController = new AuthController();
        // 审核的信息
        String uId = "hahaha";
        String orderId = newOrderId("1000998004813441");
        Date currentDate = f.parse("2026-06-18 23:49:46");

        // controller返回审核的状态，目前是三级审核还没有进行
        AuthInfo first = authController.doAuth(uId, orderId, currentDate);
        assertEquals("0001", first.getCode());
        assertInfoContains(first, orderId, "待三级审批负责人", "王工");

        // 模拟三级审核通过，后面才能进入二级审核
        AuthService.auth("1000013", orderId);

        AuthInfo second = authController.doAuth(uId, orderId, currentDate);
        assertEquals("0001", second.getCode());
        assertInfoContains(second, orderId, "待二级审批负责人", "张经理");

        AuthService.auth("1000012", orderId);
        AuthInfo third = authController.doAuth(uId, orderId, currentDate);
        assertEquals("0001", third.getCode());
        assertInfoContains(third, orderId, "待一级审批负责人", "段总");

        AuthService.auth("1000011", orderId);
        AuthInfo fourth = authController.doAuth(uId, orderId, currentDate);
        assertEquals("0001", fourth.getCode());
        assertInfoContains(fourth, orderId, "审批完成");
    }

    @Test
    public void test_AuthController_onlyLevel3RequiredOutsidePromotionDate() throws Exception {
        AuthController authController = new AuthController();
        String uId = "哈哈哈";
        String orderId = newOrderId("1000998004813442");
        Date currentDate = f.parse("2026-06-26 00:00:00");

        AuthInfo first = authController.doAuth(uId, orderId, currentDate);
        assertEquals("0001", first.getCode());
        assertInfoContains(first, orderId, "待三级审批负责人", "王工");

        AuthService.auth("1000013", orderId);
        AuthInfo second = authController.doAuth(uId, orderId, currentDate);
        assertEquals("0001", second.getCode());
        assertInfoContains(second, orderId, "审批完成");
    }

    private void assertInfoContains(AuthInfo authInfo, String... parts) {
        for (String part : parts) {
            assertTrue(
                    "实际返回：" + authInfo.getInfo() + "，期望包含：" + part,
                    authInfo.getInfo().contains(part)
            );
        }
    }

    private String newOrderId(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().replace("-", "");
    }

    @SuppressWarnings("unchecked")
    private void clearAuthServiceState() throws Exception {
        Field authMapField = AuthService.class.getDeclaredField("authMap");
        authMapField.setAccessible(true);

        Map<String, Date> authMap = (Map<String, Date>) authMapField.get(null);
        authMap.clear();
    }
}