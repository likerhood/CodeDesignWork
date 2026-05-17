package com.likerhood.design;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginSsoDecorator extends SsoInterceptor{

    private static Map<String, String> authMap = new ConcurrentHashMap<>();

    static {
        authMap.put("huahua", "queryUserInfo");
        authMap.put("doudou", "queryUserInfo");
    }

    @Override
    public boolean preHandle(String request, String response, Object handler) {
        // 1. 模拟获取cookie
        String ticket = request.substring(1, 8);

        // 2. 模拟校验
        boolean success = ticket.equals("success");

        if (!success) {
            return false;
        }

        String uId = request.substring(8);
        String method = authMap.get(uId);

        // 模拟方法校验
        return "queryUserInfo".equals(method);

    }
}
