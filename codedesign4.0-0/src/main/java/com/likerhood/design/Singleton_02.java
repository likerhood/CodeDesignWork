package com.likerhood.design;

public class Singleton_02 {

    private static Singleton_02 instance;

    private Singleton_02() {
        // 模拟第一次加载庞大的维修问答知识图谱
        System.out.println(">>> 正在初始化维修问答系统的全局知识图谱...");
    }

    // 【性能瓶颈】：直接在方法上加锁（相当于锁住了整个 Singleton_02.class）
    public static synchronized Singleton_02 getInstance() {
        if (instance == null) {
            instance = new Singleton_02();
        }
        // 对于已经创建好实例之后的几百万次调用来说，这里纯粹只是一个返回引用的“读操作”
        return instance;
    }
}
