package com.likerhood.design;

import java.util.Date;

/**
 * 模拟直接在接口中获取秒杀活动的商品信息
 * 通常包含有
 * 1. 商品id
 * 2. 商品名称
 * 3. 商品描述
 * 4. 秒杀活动的开始时间
 * 5. 秒杀活动的结束时间
 * 6. 目前的库存
 */
public class ActivityController {

    public Activity queryActivityInfo(Long id) {
        // 模拟从实际业务应用从接口中获取活动信息
        Activity activity = new Activity();
        activity.setId(10001L);
        activity.setName("图书嗨乐");
        activity.setDesc("图书优惠券分享激励分享活动第二期");
        activity.setStartTime(new Date());
        activity.setStopTime(new Date());
        activity.setStock(new Stock(1000,1));
        return activity;
    }


}
