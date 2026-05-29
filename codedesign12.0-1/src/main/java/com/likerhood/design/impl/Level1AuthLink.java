package com.likerhood.design.impl;

import com.likerhood.design.AuthInfo;
import com.likerhood.design.AuthLink;
import com.likerhood.design.AuthService;

import java.text.ParseException;
import java.util.Date;

public class Level1AuthLink extends AuthLink {

    private Date beginDate = f.parse("2026-06-11 00:00:00");
    private Date endDate = f.parse("2026-06-20 23:59:59");

    public Level1AuthLink(String levelUserId, String levelUserName) throws ParseException {
        super(levelUserId, levelUserName);
    }


    @Override
    public AuthInfo doAuth(String uId, String orderId, Date authDate) {
        Date date = AuthService.queryAuthInfo(levelUserId, orderId);
        if (null == date) {
            return new AuthInfo("0001", "单号：", orderId, " 状态：待一级审批负责人 ", levelUserName);
        }
        AuthLink next = super.getNext();
        if (null == next) {
            return new AuthInfo("0000", "单号：", orderId, " 状态：三级审批负责人完成", " 时间：", f.format(date), " 审批人：", levelUserName);
        }
        if (authDate.before(beginDate) || authDate.after(endDate)) {
            return new AuthInfo("0000", "单号：", orderId, " 状态：三级审批负责人完成", " 时间：", f.format(date), " 审批人：", levelUserName);
        }
        return next.doAuth(uId, orderId, authDate);
    }
}
