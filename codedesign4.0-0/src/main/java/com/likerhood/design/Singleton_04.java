package com.likerhood.design;

public class Singleton_04 {

    // 模拟一个外部类的普通静态变量
    public static String NORMAL_STATIC_FIELD = "我是外部类的普通静态变量";

    // 外部类的静态代码块，用于监控外部类何时被 JVM 加载
    static {
        System.out.println("【JVM 日志】 >>> 外部类 MaintenanceConfigManager 正在被加载...");
    }

    // 私有化构造器
    private Singleton_04() {
        System.out.println(Thread.currentThread().getName() + " >>> 真正执行 new 操作：维修问答系统配置项加载中（耗时50MB内存）...");
    }

    // 静态内部类：它像一个潜伏的刺客，只有被召唤时才会出动
    private static class SingletonHolder {
        // 内部类的静态代码块，用于监控内部类何时被 JVM 加载
        static {
            System.out.println("【JVM 日志】 >>> 静态内部类 SingletonHolder 正在被加载...");
        }

        // JVM 会保证这行代码的线程绝对安全！
        private static final Singleton_04 INSTANCE = new Singleton_04();
    }

    public static Singleton_04 getInstance() {
        return SingletonHolder.INSTANCE; // 只有执行到这一行，内部类才会被触发加载
    }
}
