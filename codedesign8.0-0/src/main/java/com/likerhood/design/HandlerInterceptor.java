package com.likerhood.design;

public interface HandlerInterceptor {

    boolean preHandle(String request, String response, Object handler);
}
