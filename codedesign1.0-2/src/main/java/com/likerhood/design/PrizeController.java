package com.likerhood.design;

import com.likerhood.design.store.AwardReq;
import com.likerhood.design.store.ICommodity;

public class PrizeController {

    // 模拟接受到发奖请求
    // 这里不再是面条代码了，同时后续需要新增其他类型的奖品发放，只需要新写一个继承ICommodity的接口的其他服务类就行
    public void awardToUser(AwardReq req) throws Exception {

        Integer commodityType = req.getAwardType();

        // 通过工厂获取服务
        StoreFactory storeFactory = new StoreFactory();
        ICommodity commodityService = storeFactory.getCommodityService(commodityType);
        commodityService.sendCommodity(req);


    }
}
