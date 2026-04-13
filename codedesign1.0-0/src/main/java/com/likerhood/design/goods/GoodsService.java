package com.likerhood.design.goods;

import com.alibaba.fastjson.JSON;

/**
 * 物流发货服务
 */
public class GoodsService {

    public Boolean deliverGoods(DeliverReq req) {
        System.out.println("模拟发货实物商品一个：" + JSON.toJSONString(req));
        return true;
    }

}
