package com.likerhood.design.mybatisproxy;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟 MyBatis 的 SqlSession —— 真正执行 SQL 的地方
 *
 * 真实 MyBatis 的 SqlSession 会解析 XML/注解里的 SQL，
 * 这里简化为一个 Map 模拟数据库，用方法名映射要执行的逻辑。
 */
public class SqlSession {

    // 模拟数据库表
    private static Map<Integer, String> database = new HashMap<>();

    static {
        database.put(1, "张三");
        database.put(2, "李四");
        database.put(3, "王五");
    }

    /**
     * 模拟根据方法名决定执行什么 SQL
     * 真实 MyBatis 是根据 "接口全名.方法名" 找到对应的 MappedStatement（SQL语句）
     *
     * @param methodName 方法名，比如 "selectById"、"insert"、"deleteById"
     * @param args       参数
     */
    public Object execute(String methodName, Object[] args) {
        if ("selectById".equals(methodName)) {
            int id = (int) args[0];
            String result = database.get(id);
            System.out.println("【SqlSession】执行 SQL: SELECT * FROM user WHERE id = " + id);
            System.out.println("【SqlSession】查询结果: " + result);
            return result;
        }
        if ("insert".equals(methodName)) {
            int newId = database.size() + 1;
            database.put(newId, (String) args[0]);
            System.out.println("【SqlSession】执行 SQL: INSERT INTO user VALUES (" + newId + ", '" + args[0] + "')");
            return null;
        }
        if ("deleteById".equals(methodName)) {
            int id = (int) args[0];
            database.remove(id);
            System.out.println("【SqlSession】执行 SQL: DELETE FROM user WHERE id = " + id);
            return null;
        }
        throw new RuntimeException("未知方法: " + methodName);
    }

}
