package com.likerhood.design;

import com.likerhood.design.application.CacheServiceImpl;
import com.likerhood.design.application.ICacheService;
import com.likerhood.design.factory.JDKProxyFactory;
import com.likerhood.design.workshop.impl.EGMCacheAdapter;
import com.likerhood.design.workshop.impl.IIRCacheAdapter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_CacheService() throws Exception {
        ICacheService proxy_EGM = JDKProxyFactory.getProxy(ICacheService.class, EGMCacheAdapter.class);
        proxy_EGM.set("user_name_01", "哈哈");
        String val01 = proxy_EGM.get("user_name_01");
        logger.info("缓存服务 EGM 测试，proxy_EGM.get 测试结果：{}", val01);

        ICacheService proxy_IIR = JDKProxyFactory.getProxy(ICacheService.class, IIRCacheAdapter.class);
        proxy_IIR.set("user_name_01", "哈哈");
        String val02 = proxy_IIR.get("user_name_01");
        logger.info("缓存服务 IIR 测试，proxy_IIR.get 测试结果：{}", val02);
    }

}