package com.likerhood.design.store.impl;

import com.alibaba.fastjson.JSON;
import com.likerhood.design.coupon.CouponResult;
import com.likerhood.design.coupon.CouponService;
import com.likerhood.design.store.AwardReq;
import com.likerhood.design.store.AwardRes;
import com.likerhood.design.store.ICommodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CouponCommodityService implements ICommodity {

    private Logger logger = LoggerFactory.getLogger(CouponCommodityService.class);

    // 模拟注入优惠券服务
    private CouponService couponService = new CouponService();
    public void sendCommodity(AwardReq req) throws Exception {

        CouponResult couponResult = couponService.sendCoupon(req.getuId(), req.getAwardNumber(), req.getBizId());
        AwardRes awardRes = null;
        if ("0000".equals(couponResult.getCode())){
            awardRes = new AwardRes("0000", "发放成功");
        } else{
            awardRes = new AwardRes("0001", "发放失败");
        }

        // 模拟发送响应体
        logger.info("优惠券卡发放响应体res：{}", JSON.toJSONString(awardRes));

    }
}
