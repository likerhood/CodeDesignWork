package com.likerhood.design.mybatisproxy;

import org.junit.Test;

public class MyBatisProxyTest {

    /**
     * 测试1：模拟 MyBatis 的使用方式
     *
     * 在真实 MyBatis 中你写的代码就是这样：
     *   SqlSession sqlSession = sqlSessionFactory.openSession();
     *   UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
     *   userMapper.selectById(1);
     *
     * UserMapper 根本没有实现类，MyBatis 动态代理帮你造了一个！
     */
    @Test
    public void test_mapperProxy() {
        System.out.println("===== 模拟 MyBatis Mapper 动态代理 =====\n");

        // 1. 创建 SqlSession（模拟打开数据库会话）
        SqlSession sqlSession = new SqlSession();

        // 2. 获取 Mapper —— 这里返回的是动态代理对象，不是真实实现类！
        //    对应 MyBatis 的：sqlSession.getMapper(UserMapper.class)
        UserMapper userMapper = MapperProxyFactory.getMapper(UserMapper.class, sqlSession);

        // 3. 直接调用接口方法，感觉像在调用普通对象
        //    但实际上每次调用都会被 MapperProxy.invoke() 拦截
        String user = userMapper.selectById(1);
        System.out.println("查到的用户：" + user);

        userMapper.insert("赵六");

        String newUser = userMapper.selectById(4);
        System.out.println("新插入的用户：" + newUser);

        userMapper.deleteById(2);
    }

    /**
     * 测试2：验证 userMapper 的真实身份
     *
     * 证明：虽然变量类型是 UserMapper，但实际对象是 JDK 动态生成的 $Proxy
     * 这就是为什么你不需要写实现类
     */
    @Test
    public void test_mapperIdentity() {
        SqlSession sqlSession = new SqlSession();
        UserMapper userMapper = MapperProxyFactory.getMapper(UserMapper.class, sqlSession);

        System.out.println("userMapper 的类型：" + userMapper.getClass().getName());
        // 输出：com.sun.proxy.$Proxy0  ← 不是任何手写的实现类，是 JDK 动态生成的！

        System.out.println("是否实现了 UserMapper 接口：" + (userMapper instanceof UserMapper));
        // 输出：true  ← 动态生成的类确实实现了 UserMapper
    }

}
