package com.likerhood.design.store;

public class AwardRes {

    private String code; // 编码
    private String info; // 描述


    public String getCode() {
        return code;
    }

    public AwardRes(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
