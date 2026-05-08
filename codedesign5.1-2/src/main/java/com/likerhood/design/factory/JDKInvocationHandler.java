package com.likerhood.design.factory;

import com.likerhood.design.util.ClassLoaderUtils;
import com.likerhood.design.workshop.ICacheAdapter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Method;
import java.util.Objects;

public class JDKInvocationHandler implements InvocationHandler {

    private ICacheAdapter cacheAdapter;

    public JDKInvocationHandler(ICacheAdapter cacheAdapter) {
        this.cacheAdapter = cacheAdapter;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return ICacheAdapter.class.getMethod(method.getName(), ClassLoaderUtils.getClassByArgs(args)).invoke(cacheAdapter, args);
    }
}
