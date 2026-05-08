package com.likerhood.design.factory;

import com.likerhood.design.util.ClassLoaderUtils;
import com.likerhood.design.workshop.ICacheAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JDKProxyFactory {

    public static <T> T getProxy(Class<T> cacheClazz, Class<? extends ICacheAdapter> cacheAdapter) throws InstantiationException, IllegalAccessException {

        InvocationHandler handler = new JDKInvocationHandler(cacheAdapter.newInstance());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        return (T) Proxy.newProxyInstance(classLoader, new Class[]{cacheClazz}, handler);

    }

}
