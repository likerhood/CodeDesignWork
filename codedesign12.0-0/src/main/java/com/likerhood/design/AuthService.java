package com.likerhood.design;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthService {


    // 保存审批记录
    private static Map<String, Date> authMap = new ConcurrentHashMap<>();

    public static Date queryAuthInfo(String uId, String orderId){

        return authMap.get(uId.concat(orderId));
    }


    public static void auth(String uId, String orderId){
        authMap.put(uId.concat(orderId), new Date());
    }


}
