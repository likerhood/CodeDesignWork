package com.likerhood.design.coupon;

/**
 * 优惠券返回结果
 * 调用优惠券平台发券后，平台会告诉你"这次发券成不成功"。
 */
public class CouponResult {
    private String code;    // "0000"标识发放优惠券成功，其他标识是失败
    private String info;    // 描述信息，比如发放成功或者用户不存在

    public CouponResult(String code, String info) {
        this.code = code;
        this.info = info;
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
