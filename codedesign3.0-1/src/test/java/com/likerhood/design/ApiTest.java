package com.likerhood.design;

import static org.junit.Assert.*;

public class ApiTest {


    // 创建三分一模一样的试卷
    // 每次创建一份都会去请求服务器的数据库（controller类假设会请求和接受服务器信息）
    // 消耗资源，耗时耗力
    @org.junit.Test
    public void createPaper() {
        QuestionBankController questionBankController = new QuestionBankController();
        System.out.println(questionBankController.createPaper("花花", "1000001921032"));
        System.out.println(questionBankController.createPaper("豆豆", "1000001921051"));
        System.out.println(questionBankController.createPaper("大宝", "1000001921987"));

    }
}