package com.likerhood.design.channel;

import com.likerhood.design.mode.PayFaceMode;
import com.likerhood.design.mode.PayFingerPrintMode;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ApiTest {
    @Test
    public void test_pay() {

        System.out.println("\r\n模拟测试场景；微信支付、人脸方式。");
        Pay wxPay = new WxPay(new PayFaceMode());
        wxPay.transfer("weixin_1092033111", "100000109893", new BigDecimal(100));

        System.out.println("\r\n模拟测试场景；支付宝支付、指纹方式。");
        Pay zfbPay = new ZfbPay(new PayFingerPrintMode());
        zfbPay.transfer("jlu19dlxo111", "100000109894", new BigDecimal(100));

    }
}