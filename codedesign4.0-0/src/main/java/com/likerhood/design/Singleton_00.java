package com.likerhood.design;

/**
 * 饿汉式，单例模式
 */
public class Singleton_00 {

    // 假设这是一个其他的静态变量
    public static final String SOME_OTHER_STATIC_FIELD = new String("饿汉式单例模式");

    // 1. 静态变量，在类加载时初始化
    private static final Singleton_00 INSTANCE = new Singleton_00();

    // 2. 私有化构造器，里面模拟“耗时且占用资源”的操作
    private Singleton_00() {
        System.out.println(">>> 构造器被调用！Singleton_00实例正在被创建...");
        System.out.println(">>> 正在加载 50MB 配置文件...");
        System.out.println(">>> 正在建立数据库连接...\n");
    }

    // 3. 提供全局访问点
    public static Singleton_00 getInstance() {
        System.out.println("--- getInstance() 方法被调用 ---");
        return INSTANCE;
    }

    public void doSomething() {
        System.out.println("执行饿汉式单例对象的核心业务逻辑。");
    }
}
