package com.likerhood.design.store.impl;

import com.alibaba.fastjson.JSON;
import com.likerhood.design.goods.DeliverReq;
import com.likerhood.design.goods.GoodsService;
import com.likerhood.design.store.AwardReq;
import com.likerhood.design.store.AwardRes;
import com.likerhood.design.store.ICommodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoodsCommodityService implements ICommodity {

    private Logger logger = LoggerFactory.getLogger(CardCommodityService.class);

    // 模拟注入实体商品发放服务类
    private GoodsService goodsService = new GoodsService();

    public void sendCommodity(AwardReq req) throws Exception {
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

        AwardRes awardRes = null;

        // 返回发放结果发奖响应体
        if (isSuccess) {
            awardRes = new AwardRes("0000", "发放成功");
        }else{
            awardRes = new AwardRes("0001", "发放失败");
        }

        // 模拟发送响应体
        logger.info("实体商品发放响应体res：{}", JSON.toJSONString(awardRes));

    }



    private String queryUserName(String uId) {
        return "花花";
    }

    private String queryUserPhoneNumber(String uId) {
        return "15200101232";
    }




}
