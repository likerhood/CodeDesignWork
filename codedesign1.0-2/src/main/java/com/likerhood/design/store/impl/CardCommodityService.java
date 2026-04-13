package com.likerhood.design.store.impl;

import com.alibaba.fastjson.JSON;
import com.likerhood.design.store.AwardReq;
import com.likerhood.design.store.AwardRes;
import com.likerhood.design.store.ICommodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.likerhood.design.card.IQIYiCardService;

import java.util.Map;

public class CardCommodityService implements ICommodity {

    private Logger logger = LoggerFactory.getLogger(CardCommodityService.class);

    // 模拟注入,生成爱奇艺卡服务类
    private IQIYiCardService iQiYiCardService = new IQIYiCardService();

    public void sendCommodity(AwardReq req) throws Exception {
        String bindMoblieNumber = queryUserMobile(req.getuId());
        // 调用爱奇艺卡服务类，给用户编号授权开放这个卡的权限
        iQiYiCardService.grantToken(bindMoblieNumber, req.getAwardNumber());
        // 生成发奖的响应体类
        AwardRes awardRes = new AwardRes("0000", "发放成功");

        // 模拟发送响应体
        logger.info("第三方会员卡发放响应体res：{}", JSON.toJSONString(awardRes));
    }


    private String queryUserMobile(String uId) {
        return "15200101232";
    }
}
