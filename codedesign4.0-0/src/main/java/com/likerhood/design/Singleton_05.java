package com.likerhood.design;


/**
 * 枚举单例：维纳斯系统 - 诊断历史同步引擎
 * 注意：枚举天生实现了 Serializable 接口，所以可以直接被序列化
 */
public enum Singleton_05 {

    // 1. 定义一个枚举元素，它就是那个全局唯一的实例
    INSTANCE;

    // 2. 内部的业务状态
    private int syncCount = 0;

    // 3. 具体的业务方法
    public void doDiagnosticHistorySync(String patientId) {
        syncCount++;
        System.out.println("正在同步患者 [" + patientId + "] 的诊断影像历史... (当前同步总数: " + syncCount + ")");
    }

    public int getSyncCount() {
        return syncCount;
    }
}