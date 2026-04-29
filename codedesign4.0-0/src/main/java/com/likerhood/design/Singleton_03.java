package com.likerhood.design;

public class Singleton_03 {

    // 【核心重点 1】：volatile 严防指令重排（禁止 1->3->2）
    private static volatile Singleton_03 instance;

    // 【核心重点 2】：私有化构造器
    private Singleton_03() {
        System.out.println(Thread.currentThread().getName() + " 真正执行了 new 操作，创建了单例！");
    }

    // 【核心重点 3】：全局访问点
    public static Singleton_03 getInstance() {
        // 第一重检查：如果已经创建好了，直接返回，避开下面沉重的 synchronized 锁
        if (instance == null) {

            // 加锁：确保只有第一个冲进来的线程能去创建对象
            synchronized (Singleton_03.class) {

                // 第二重检查：防止后面的线程排队进来后重复创建
                if (instance == null) {

                    // 这里的底层逻辑：1.分配空间 -> 2.初始化 -> 3.指针赋值
                    // 因为加了 volatile，绝对保证按 1->2->3 执行
                    instance = new Singleton_03();
                }
            }
        }
        return instance;
    }
}
