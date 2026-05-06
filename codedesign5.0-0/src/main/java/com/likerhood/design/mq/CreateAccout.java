package com.likerhood.design.mq;

import java.util.Date;
import com.alibaba.fastjson.JSON;

// 开户类
public class CreateAccout {

    private String number;  // 开户编号
    private String address;     // 地址
    private Date accountDate;   // 开户时间
    private String desc;    // 开户描述

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date account) {
        accountDate = account;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
