package com.likerhood.design;

import com.alibaba.fastjson.JSON;
import com.likerhood.design.store.AwardReq;
import com.likerhood.design.store.AwardRes;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void awardToUser() throws Exception {
        PrizeController prizeController = new PrizeController();

        // 1. 模拟发放优惠券测试
        System.out.println("\r\n模拟发放优惠券测试\r\n");
        AwardReq req01 = new AwardReq();
        req01.setuId("10001");
        req01.setAwardType(1);
        req01.setAwardNumber("EGM1023938910232121323432");
        req01.setBizId("791098764902132");
        prizeController.awardToUser(req01);



        // 2. 模拟方法实物商品
        System.out.println("\r\n模拟方法实物商品\r\n");
        AwardReq req02 = new AwardReq();
        req02.setuId("10001");
        req02.setAwardType(2);
        req02.setAwardNumber("9820198721311");
        req02.setBizId("1023000020112221113");
        req02.setExtMap(new HashMap<String, String>() {{
            put("consigneeUserName", "谢飞机");
            put("consigneeUserPhone", "15200292123");
            put("consigneeUserAddress", "吉林省.长春市.双阳区.XX街道.檀溪苑小区.#18-2109");
        }});

        prizeController.awardToUser(req02);



        // 3. 模拟第三方瑞换卡（爱奇艺）
        System.out.println("\r\n第三方兑换卡(爱奇艺)\r\n");
        AwardReq req03 = new AwardReq();
        req03.setuId("10001");
        req03.setAwardType(3);
        req03.setAwardNumber("AQY1xjkUodl8LO975GdfrYUio");

        prizeController.awardToUser(req03);

    }
}