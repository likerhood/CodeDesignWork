package com.likerhood.design.mybatisproxy;

import java.lang.reflect.Proxy;

/**
 * 模拟 MyBatis 的 MapperProxyFactory + SqlSessionFactory
 *
 * 对应 MyBatis 源码：org.apache.ibatis.binding.MapperProxyFactory
 *
 * 职责：给定一个 Mapper 接口的 Class，动态生成它的实现对象
 */
public class MapperProxyFactory {

    /**
     * 获取 Mapper 代理对象
     * 对应 MyBatis 中的：sqlSession.getMapper(UserMapper.class)
     *
     * @param mapperClass Mapper 接口的 Class（如 UserMapper.class）
     * @param sqlSession  执行 SQL 的会话
     * @param <T>         Mapper 接口类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMapper(Class<T> mapperClass, SqlSession sqlSession) {

        // 创建 MapperProxy，它持有 sqlSession 用来执行 SQL
        MapperProxy mapperProxy = new MapperProxy(sqlSession);

        // 动态生成一个实现了 mapperClass 接口的代理对象
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{mapperClass},   // 代理对象要实现的接口
                mapperProxy                 // 所有方法调用由 mapperProxy 处理
        );
    }

}
