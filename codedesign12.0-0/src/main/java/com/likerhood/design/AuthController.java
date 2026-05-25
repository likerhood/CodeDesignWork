package com.likerhood.design;

import sun.java2d.pipe.SpanShapeRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthController {

    private SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //时间格式化

    public AuthInfo doAuth(String uId, String orderId, Date authdate) throws ParseException {

        // 三级审批
        Date date = AuthService.queryAuthInfo("1000013", orderId);
        // 返回null说明是新的记录，需要开始审核
        if (null == date)   return new AuthInfo("0001", "单号", orderId, " 状态，等待三级审批负责人 ", "王工");

        // 二级审批
        if (authdate.after(f.parse("2026-06-01 00:00:00")) && authdate.before(f.parse("2026-06-25 23:59:59"))) {
            date = AuthService.queryAuthInfo("1000012", orderId);
            if (null == date) return new AuthInfo("0001", "单号", orderId, " 状态：等待二级审批负责人 ", "张经理");
        }

        // 一级审批
        if(authdate.after(f.parse("2026-06-11 00:00:00")) && authdate.before(f.parse("2026-06-20 23:59:59"))){
            date = AuthService.queryAuthInfo("1000011", orderId);
            if (null == date) return new AuthInfo("0001", "单号", orderId, " 状态：等待一级审批负责人 ", "段总");
        }



        return new AuthInfo("0001", "单号:", orderId, " 状态：审批完成");
    }


}
