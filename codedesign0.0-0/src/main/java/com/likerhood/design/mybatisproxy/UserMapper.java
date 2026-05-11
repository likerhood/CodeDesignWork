package com.likerhood.design.mybatisproxy;

/**
 * Mapper 接口 —— 模拟 MyBatis 中的 UserMapper
 *
 * 注意：这里只有接口，没有任何实现类！
 * MyBatis 会在运行时通过动态代理自动生成实现类。
 */
public interface UserMapper {

    // 方法名 + 参数，就是"SQL 的线索"
    String selectById(int id);

    void insert(String username);

    void deleteById(int id);

}
