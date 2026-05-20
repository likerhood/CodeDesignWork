package com.likerhood.design;

import com.likerhood.design.util.RedisUtils;

public class ActivateController {

    private RedisUtils redisUtils = new RedisUtils();

    public Activity queryActivityInfo(Long id) {
        // 静态实例
        Activity activity = ActivityFactory.getActivity(id);
        // 模拟从Redis中获取库存变化信息
        Stock stock = new Stock(1000, redisUtils.getStockUsed());
        activity.setStock(stock);
        return activity;
    }


}
