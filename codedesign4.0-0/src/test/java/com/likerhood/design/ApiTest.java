package com.likerhood.design;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
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



    public static void test_singleton_02() throws InterruptedException {
        System.out.println("========== 维修问答系统并发性能压测：有锁 VS 无锁 ==========\n");

        int threadCount = 100;       // 100个并发线程
        int loopCount = 1_000_000;   // 每个线程调用 100万次获取实例的方法

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // ================= 第 1 轮：测试无锁的获取方式（对照组） =================
        // 先触发一次类加载，排除初始化耗时干扰
        Singleton_00.getInstance();

        CountDownLatch startLatch1 = new CountDownLatch(1);
        CountDownLatch endLatch1 = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    startLatch1.await();
                    // 模拟高频次获取实例进行知识库检索
                    for (int j = 0; j < loopCount; j++) {
                        Singleton_00.getInstance(); // 无锁调用
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    endLatch1.countDown();
                }
            });
        }

        long startTime1 = System.currentTimeMillis();
        startLatch1.countDown(); // 发令枪响
        endLatch1.await();       // 等待所有线程跑完
        long endTime1 = System.currentTimeMillis();
        System.out.println("✅ [无锁获取] 100个线程各获取 100万次，总耗时: " + (endTime1 - startTime1) + " ms");


        // ================= 第 2 轮：测试同步锁方法（性能瓶颈组） =================
        // 先触发一次初始化，确保接下来的测试全是“读操作”
        Singleton_02.getInstance();

        CountDownLatch startLatch2 = new CountDownLatch(1);
        CountDownLatch endLatch2 = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    startLatch2.await();
                    // 模拟高频次获取实例进行知识库检索
                    for (int j = 0; j < loopCount; j++) {
                        Singleton_02.getInstance(); // 有锁调用！
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    endLatch2.countDown();
                }
            });
        }

        long startTime2 = System.currentTimeMillis();
        startLatch2.countDown(); // 发令枪响
        endLatch2.await();       // 等待所有线程跑完
        long endTime2 = System.currentTimeMillis();
        System.out.println("❌ [同步锁获取] 100个线程各获取 100万次，总耗时: " + (endTime2 - startTime2) + " ms");

        executor.shutdown();
    }

    public static void test_singleton_03() throws InterruptedException {
        System.out.println("========== DCL 单例并发安全测试 ==========\n");

        int threadCount = 100; // 100 个并发线程

        CountDownLatch startLatch = new CountDownLatch(1); // 发令枪
        CountDownLatch endLatch = new CountDownLatch(threadCount); // 终点线

        // 用线程安全的 Set 收集获取到的实例
        Set<Singleton_03> instanceSet = ConcurrentHashMap.newKeySet();

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // 所有线程在此等待发令枪

                    // 去获取 DCL 单例
                    Singleton_03 parser = Singleton_03.getInstance();

                    // 存入集合中
                    instanceSet.add(parser);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    endLatch.countDown(); // 跑到终点
                }
            });
        }

        // 发令枪响，100个线程瞬间并发！
        startLatch.countDown();

        // 等待所有线程执行完毕
        endLatch.await();
        executor.shutdown();

        System.out.println("\n========== 测试结果 ==========");
        System.out.println("并发线程数: " + threadCount);
        System.out.println("Set 集合中实例的数量: " + instanceSet.size());

        if (instanceSet.size() == 1) {
            System.out.println("✅ 测试通过！完美的单例，多线程下依然绝对安全。");
        } else {
            System.err.println("❌ 测试失败！单例被打破了！");
        }
    }
    public static void test_singleton_04() throws InterruptedException {
        System.out.println("========== 阶段一：验证【懒加载】特性 ==========\n");

        System.out.println("业务代码：我现在仅仅想读取一下外部类的普通变量...");
        String temp = Singleton_04.NORMAL_STATIC_FIELD;
        System.out.println("业务代码获取完成。\n");

        // 模拟系统运行了一段时间...
        Thread.sleep(1000);

        System.out.println("========== 阶段二：验证【高并发安全】特性 ==========\n");
        System.out.println("业务代码：100 个请求同时涌入维修问答系统，同时索要配置管理器！\n");

        int threadCount = 100;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadCount);
        Set<Singleton_04> instanceSet = ConcurrentHashMap.newKeySet();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // 100个线程在此阻塞等待发令枪

                    // 核心：并发调用 getInstance()
                    Singleton_04 manager = Singleton_04.getInstance();
                    instanceSet.add(manager);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    endLatch.countDown();
                }
            });
        }

        // 发令枪响！
        startLatch.countDown();
        endLatch.await();
        executor.shutdown();

        System.out.println("\n========== 测试结果统计 ==========");
        System.out.println("并发线程数: " + threadCount);
        System.out.println("Set 集合中实例的数量: " + instanceSet.size());

        if (instanceSet.size() == 1) {
            System.out.println("✅ 测试通过！完美的单例，既实现了懒加载，又保证了绝对的并发安全。");
        }
    }
    public static void test_singleton_05() throws InterruptedException {
        System.out.println("========== 维纳斯系统防御测试：枚举单例的终极考验 ==========\n");

        Singleton_05 originalInstance = Singleton_05.INSTANCE;
        originalInstance.doDiagnosticHistorySync("P-001"); // 初始状态，同步数 = 1

        // ================= 攻击一：反射暴力破解 =================
        System.out.println("\n[攻击一] 黑客尝试使用反射机制暴力破解单例...");
        try {
            // 枚举的底层其实有一个 (String name, int ordinal) 的隐式构造器
            Constructor<Singleton_05> constructor =
                    Singleton_05.class.getDeclaredConstructor(String.class, int.class);

            // 强行关闭安全检查！
            constructor.setAccessible(true);

            // 尝试强行 new 一个新的枚举实例
            System.out.println("准备执行 constructor.newInstance()...");
            Singleton_05 evilInstance = constructor.newInstance("EVIL_INSTANCE", 1);

        } catch (Exception e) {
            System.err.println("🛡️ 攻击失败！底层 JVM 拦截了反射创建枚举的请求！");
            System.err.println("拦截原因: " + e.getCause());
        }

        // ================= 攻击二：序列化克隆破坏 =================
        System.out.println("\n[攻击二] 黑客尝试使用序列化与反序列化来克隆单例对象...");
        Singleton_05 clonedInstance = null;
        try {
            // 1. 将原实例写入内存数组（模拟写入磁盘/网络传输）
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(originalInstance);
            oos.close();

            // 2. 从内存数组中重新读取出来（模拟反序列化）
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            clonedInstance = (Singleton_05) ois.readObject();
            ois.close();

            System.out.println("克隆完成！正在比对克隆对象与原对象...");

            // 3. 验证两个对象是否是同一个内存地址
            if (originalInstance == clonedInstance) {
                System.out.println("🛡️ 攻击失败！反序列化出来的依然是原本内存中的那个实例！");
            } else {
                System.err.println("❌ 灾难爆发！单例被克隆破坏了！");
            }

            // 4. 验证状态是否一致
            System.out.println("原对象同步总数: " + originalInstance.getSyncCount());
            System.out.println("反序列化对象的同步总数: " + clonedInstance.getSyncCount());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws InterruptedException{

//        test_singleton_00();
//        test_singleton_01();

//        test_singleton_02();
//        test_singleton_03();
//        test_singleton_04();
        test_singleton_05();

    }


}