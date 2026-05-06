package com.likerhood.design.jsonparseobject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class main {

    // 1. 解析实体类内部的泛型集合
    public static void test1() throws IOException {

        // 1. 读取本地 test.json 文件（包含多个 Agent 响应的数组）
        String jsonStr = new String(Files.readAllBytes(Paths.get("D:\\desktop\\1\\CodeDesignWork\\codedesign0.0-0\\test.json")));

        // 2. 先解析为 List 集合
        List<AgentResponse> list = JSON.parseObject(jsonStr, new TypeReference<List<AgentResponse>>(){});

        // 3. 取出第一个对象来验证“内部嵌套泛型”
        AgentResponse response = list.get(0);

        // 4. 核心验证：测试能否正常拿取内部嵌套的 Action 对象
        // 如果泛型被擦除，这里拿到的会是 JSONObject，调用 getActionName() 会直接报错
        System.out.println("外层意图: " + response.getIntent());
        System.out.println("内层第一个动作名: " + response.getActions().get(0).getActionName());
        System.out.println("内层第一个动作目标: " + response.getActions().get(0).getTarget());
    }


    // 2.  直接解析顶层匿名泛型集合
    public static void test2() throws IOException {
        // 1. 读取我们的 test.json 文件（一个完整的 [] 数组字符串）
        String jsonArrayString = new String(Files.readAllBytes(Paths.get("D:\\desktop\\1\\CodeDesignWork\\codedesign0.0-0\\test.json")));

        // 2. ❌ 致命错误示范：直接用 List.class 去接
        List<AgentResponse> list = JSON.parseObject(jsonArrayString, List.class);

        // 3. 表面上看没有报错，它甚至能打印出正确的大小！
        System.out.println("集合大小: " + list.size());

        // 4. 🚨 运行时爆炸：这一步必定抛出 ClassCastException 异常！
        AgentResponse firstResponse = list.get(0);
    }


    public static void test3() throws IOException {
        // 1. 读取我们一开始准备的 JSON 测试文件
        String jsonStr = new String(Files.readAllBytes(Paths.get("D:\\desktop\\1\\CodeDesignWork\\codedesign0.0-0\\test.json")));

        // 2. ✅ 正确示范：使用带大括号 {} 的 TypeReference 锁死完整的泛型树
        List<AgentResponse> list = JSON.parseObject(
                jsonStr,
                new TypeReference<List<AgentResponse>>() {}
        );

        // 3. 验证成果
        for (AgentResponse agent : list) {
            System.out.println("----------");
            System.out.println("Agent 意图: " + agent.getIntent());
            System.out.println("第一个动作目标: " + agent.getActions().get(0).getTarget());
            System.out.println("执行置信度: " + agent.getConfidenceScores());
        }
    }


    public static void main(String[] args) throws IOException {
        test1();
//        test2();
        test3();
    }





}
