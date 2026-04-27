package com.likerhood.design;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class ApiTest {

    public static void test_singleton_00(){

        System.out.println("========== 系统启动 ==========\n");

        // 模拟系统运行了一段时间，我们仅仅是访问了这个类的另一个静态变量
        System.out.println("业务代码：我尝试访问 HeavyEagerSingleton 的另一个静态变量...");
        String temp = Singleton_00.SOME_OTHER_STATIC_FIELD;
        System.out.println("业务代码获取到的值: " + temp);
        // 注意：我们并没有调用 HeavyEagerSingleton.getInstance() ！！！
        System.out.println("此时没有在该方法中接受饿汉式单例模式的实例对象，但是该实例对象已经被创建\n");

        // 直到此刻，我们才真正需要用到这个单例对象
        System.out.println("业务代码：我现在真正需要饿汉式单例模式的单例对象了！");
        Singleton_00 instance1 = Singleton_00.getInstance();
        Singleton_00 instance2 = Singleton_00.getInstance();

        System.out.println("\ninstance1 和 instance2 是同一个对象吗？ " + (instance1 == instance2));

        instance1.doSomething();

        System.out.println("饿汉式单例模式执行完成");
    }


    public static void test_singleton_01() throws InterruptedException{

        System.out.println("========== 懒汉式单例模式线程不安全版本场景模拟：\n 维纳斯系统启动：高并发获取影像解析引擎 ==========\n");

        int threadCount = 100; // 模拟 100 个并发请求

        // 发令枪：用于让所有线程在同一起跑线等待
        CountDownLatch startLatch = new CountDownLatch(1);
        // 终点线：用于等待所有线程执行完毕
        CountDownLatch endLatch = new CountDownLatch(threadCount);

        // 线程安全的 Set，用来收集各个线程获取到的实例
        // 如果是真正的单例，最后 Set 的 size 应该是 1
        Set<Singleton_01> instanceSet = ConcurrentHashMap.newKeySet();

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    // 1. 所有线程在此阻塞，等待发令枪倒计时归零
                    startLatch.await();

                    // 2. 发令枪响，大家同时去获取单例！
                    Singleton_01 parser = Singleton_01.getInstance();

                    // 3. 将获取到的对象放入 Set 中（Set 会利用对象的内存地址自动去重）
                    instanceSet.add(parser);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 4. 线程执行完毕，终点线计数减 1
                    endLatch.countDown();
                }
            });
        }

        // 100个线程瞬间并发执行
        startLatch.countDown();

        // 主线程等待所有子线程跑到终点
        endLatch.await();
        executor.shutdown();

        System.out.println("\n========== 测试结果统计 ==========");
        System.out.println("并发请求数量: " + threadCount);
        System.out.println("实际创建的影像解析引擎实例数量: " + instanceSet.size());

        if (instanceSet.size() > 1) {
            System.err.println("❌ 灾难发生：单例被打破！内存中出现了多个不同的实例！");
            for (Singleton_01 instance : instanceSet) {
                System.out.println("实例内存地址：" + instance);
            }
        } else {
            System.out.println("✅ 单例正常工作。");
        }



    }


    public static void main(String[] args) throws InterruptedException{

//        test_singleton_00();
        test_singleton_01();
    }

}