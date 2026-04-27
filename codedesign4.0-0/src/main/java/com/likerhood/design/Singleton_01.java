package com.likerhood.design;

/**
 * 懒汉式单例（线程不安全版）
 * 场景：维纳斯系统 - 全局诊断影像解析引擎
 */
public class Singleton_01 {

    private static Singleton_01 instance;

    private Singleton_01() {
        System.out.println(Thread.currentThread().getName() + " >>> 正在分配内存，初始化诊断影像解析引擎...");
        try {
            // 【关键点】：模拟引擎初始化的耗时操作，也是为了故意放大并发漏洞
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Singleton_01 getInstance() {
        // 步骤 1：判断实例是否为空
        if (instance == null) {

            // 【极其危险的区域】：在高并发下，如果有多个线程同时通过了上面的 null 判断，
            // 它们会在这里排队执行 new 操作。
            // 为了让测试更容易暴露出问题，我们让线程在这里稍微停顿一下（模拟时间片耗尽）
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 步骤 2：创建实例
            instance = new Singleton_01();
        }
        return instance;
    }
}