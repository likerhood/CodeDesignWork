package com.likerhood.design;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class ApiTest {
    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_CacheServiceAfterImpl() {
        ICacheService cacheService = new CacheClusterServiceImpl();

        cacheService.set("user_name_01", "like", 1);
        String val01 = cacheService.get("user_name_01", 1);
        logger.info("缓存集群升级，测试结果：{}", val01);
    }
}