package com.likerhood.design;


import com.alibaba.fastjson.JSON;
import com.likerhood.design.card.IQIYiCard;
import com.likerhood.design.card.IQIYiCardService;
import com.likerhood.design.coupon.CouponResult;
import com.likerhood.design.coupon.CouponService;
import com.likerhood.design.goods.DeliverReq;
import com.likerhood.design.goods.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrizeController {

    // 日志打印
    private Logger logger = LoggerFactory.getLogger(PrizeController.class);

    // 接受法将请求
    public AwardRes awardToUser(AwardReq req) {
        // 1. 将请求体类json序列化
        String reqJson = JSON.toJSONString(req);
        // 2. 定义res响应体类
        AwardRes awardRes = null;
        // 3. 开始处理请求
        try {
            logger.info("奖品发放开始{}。 req:{}", req.getuId(), reqJson);
            // 按照不同类型方法商品[1优惠券、2实物商品、3第三方兑换卡(爱奇艺)]
            if (req.getAwardType() == 1) {
                CouponService couponService = new CouponService();
                CouponResult couponResult = couponService.sendCoupon(req.getuId(), req.getAwardNumber(), req.getBizId());
                if ("0000".equals(couponResult.getCode())){
                    awardRes = new AwardRes("0000", "发放成功");
                } else{
                    awardRes = new AwardRes("0001", "发放失败");
                }

            } else if (req.getAwardType() == 2) {
                // 现在是实体奖品发放，调用GoodsService的时候还要生成一个请求类
                // 实际应用中在GoodService中需要处理物流的请求类，然后接受物流的响应体，再给出是否发货等等
                GoodsService goodsService = new GoodsService();
                DeliverReq deliverReq = new DeliverReq();
                // 封装物流请求类
                deliverReq.setUserName(queryUserName(req.getuId()));
                deliverReq.setUserPhone(queryUserPhoneNumber(req.getuId()));
                deliverReq.setSku(req.getAwardNumber());
                deliverReq.setOrderId(req.getBizId());
                deliverReq.setConsigneeUserName(req.getExtMap().get("consigneeUserName"));
                deliverReq.setConsigneeUserPhone(req.getExtMap().get("consigneeUserPhone"));
                deliverReq.setConsigneeUserAddress(req.getExtMap().get("consigneeUserAddress"));
                // 调用GoodsService看物流发货是否成功
                Boolean isSuccess = goodsService.deliverGoods(deliverReq);

                // 返回发放结果发奖响应体
                if (isSuccess) {
                    awardRes = new AwardRes("0000", "发放成功");
                }else{
                    awardRes = new AwardRes("0001", "发放失败");
                }
            } else if (req.getAwardType() == 3) {
                String bindMoblieNumber = queryUserPhoneNumber(req.getuId());
                // 生成爱奇艺卡服务类
                IQIYiCardService iqiYiCardService = new IQIYiCardService();
                // 调用爱奇艺卡服务类，给用户编号授权开放这个卡的权限
                iqiYiCardService.grantToken(bindMoblieNumber, req.getAwardNumber());
                // 生成发奖的响应体类
                awardRes = new AwardRes("0000", "发放成功");

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // 返回响应体
        return awardRes;

    }


    private String queryUserName(String uId) {
        return "花花";
    }

    private String queryUserPhoneNumber(String uId) {
        return "15200101232";
    }



}
