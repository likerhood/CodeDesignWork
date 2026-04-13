package com.likerhood.design;


import com.likerhood.design.store.ICommodity;
import com.likerhood.design.store.impl.CardCommodityService;
import com.likerhood.design.store.impl.CouponCommodityService;
import com.likerhood.design.store.impl.GoodsCommodityService;

/**
 * 工厂类：根据不同场景返回不同的服务类对象，
 * 但是服务类都实现了相同的接口，实现了必须要求的接口方法
 * 调用工厂的乙方直接传入类型，得到服务类对象，然后调用服务类对象的方法
 */
public class StoreFactory {

    /**
     * 奖品类型方式实例化
     * @param commodityType 奖品类型
     * @return 实例化对象
     */
    public ICommodity getCommodityService(Integer commodityType){
        if (null == commodityType)  return null;
        if (1 == commodityType) return new CouponCommodityService();
        if (2 == commodityType) return new GoodsCommodityService();
        if (3 == commodityType) return new CardCommodityService();
        throw new RuntimeException("不存在的奖品服务类型");
    }


    /**
     * 奖品类信息方式实例化
     * @param clazz 奖品类
     * @return      实例化对象
     */
    public ICommodity getCommodityService(Class<? extends ICommodity> clazz) throws IllegalAccessException, InstantiationException {
        if (null == clazz) return null;
        return clazz.newInstance();
    }



}
