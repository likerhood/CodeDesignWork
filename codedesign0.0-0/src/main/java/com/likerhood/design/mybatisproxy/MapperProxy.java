package com.likerhood.design.mybatisproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 模拟 MyBatis 的 MapperProxy —— 核心！
 *
 * 对应 MyBatis 源码：org.apache.ibatis.binding.MapperProxy
 *
 * 当你调用 userMapper.selectById(1) 时：
 *   1. 实际调用的是 JDK 动态生成的 $Proxy 对象
 *   2. $Proxy 把调用转发到这里的 invoke()
 *   3. 这里拿到方法名，交给 SqlSession 去执行真正的 SQL
 *
 * 你永远不需要写 UserMapper 的实现类，MyBatis 帮你"凭空造"了一个。
 */
public class MapperProxy implements InvocationHandler {

    // 持有 SqlSession，通过它执行真正的 SQL
    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     * 每当代理对象的任何方法被调用，都会进入这里
     *
     * @param proxy  代理对象（就是那个动态生成的 $Proxy）
     * @param method 被调用的方法（比如 selectById）
     * @param args   参数（比如 id=1）
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 过滤掉 Object 本身的方法（如 toString、hashCode），不需要拦截
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        System.out.println("\n【MapperProxy】拦截到方法调用: " + method.getName());
        System.out.println("【MapperProxy】转交给 SqlSession 执行...");

        // 核心：把方法名和参数交给 SqlSession，由它决定执行什么 SQL
        return sqlSession.execute(method.getName(), args);
    }

}
