package com.likerhood.design;

public class AuthInfo {

    private String code;    // 0000表示审核失败，0001表示审核成功
    private String info = "";

    public AuthInfo(String code, String ...infos){
        this.code = code;
        for (String str : infos) {
            this.info = this.info.concat(str);
        }
    }

    public String getCode() {
        return code;
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
