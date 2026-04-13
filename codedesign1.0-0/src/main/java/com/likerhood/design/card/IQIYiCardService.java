package com.likerhood.design.card;

/**
 * @description：模拟爱奇艺会员卡的服务
 */
public class IQIYiCardService {
    public void grantToken(String bindMobileNumber, String cardId){
        System.out.println("模拟发放会员卡一张： " + bindMobileNumber + " " + cardId);
    }
}
