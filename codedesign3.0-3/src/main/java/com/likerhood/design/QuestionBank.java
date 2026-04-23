package com.likerhood.design;


// 为生成试卷提供服务
// 要实现克隆方法

import com.likerhood.design.utils.Topic;
import com.likerhood.design.utils.TopicRandomUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class QuestionBank implements Cloneable{

    private String candidate;  // 考生姓名
    private String number;  // 学号

    // 选择题列表
    private ArrayList<ChoiceQuestion> choiceQuestionsList = new ArrayList<>();
    private ArrayList<AnswerQuestion> answerQuestionsList = new ArrayList<>();

    // 链式调用，快速添加题目
    public QuestionBank append(ChoiceQuestion choiceQuestion){
        choiceQuestionsList.add(choiceQuestion);
        return this;
    }

    public QuestionBank append(AnswerQuestion answerQuestion){
        answerQuestionsList.add(answerQuestion);
        return this;
    }

    // 重写clone方法
    @Override
    public QuestionBank clone() throws CloneNotSupportedException{
        QuestionBank questionBank = (QuestionBank) super.clone();
        questionBank.choiceQuestionsList = (ArrayList<ChoiceQuestion>) choiceQuestionsList.clone();
        questionBank.answerQuestionsList = (ArrayList<AnswerQuestion>) answerQuestionsList.clone();

        // 题目乱序
        Collections.shuffle(choiceQuestionsList);
        Collections.shuffle(answerQuestionsList);

        // 答案乱序
        ArrayList<ChoiceQuestion> choiceQuestionsList = questionBank.choiceQuestionsList;
        for (ChoiceQuestion choiceQuestion : choiceQuestionsList) {
            Topic newTopic = TopicRandomUtil.random(choiceQuestion.getOption(), choiceQuestion.getKey());
            choiceQuestion.setOption(newTopic.getOption());
            choiceQuestion.setKey(newTopic.getKey());
        }

        return questionBank;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        StringBuilder detail = new StringBuilder("考生：" + candidate + "\r\n" +
                "考号：" + number + "\r\n" +
                "--------------------------------------------\r\n" +
                "一、选择题" + "\r\n\n");

        for (int idx = 0; idx < choiceQuestionsList.size(); idx++) {
            detail.append("第").append(idx + 1).append("题：").append(choiceQuestionsList.get(idx).getName()).append("\r\n");
            Map<String, String> option = choiceQuestionsList.get(idx).getOption();
            for (String key : option.keySet()) {
                detail.append(key).append("：").append(option.get(key)).append("\r\n");;
            }
            detail.append("答案：").append(choiceQuestionsList.get(idx).getKey()).append("\r\n\n");
        }

        detail.append("二、问答题" + "\r\n\n");

        for (int idx = 0; idx < answerQuestionsList.size(); idx++) {
            detail.append("第").append(idx + 1).append("题：").append(answerQuestionsList.get(idx).getName()).append("\r\n");
            detail.append("答案：").append(answerQuestionsList.get(idx).getKey()).append("\r\n\n");
        }

        return detail.toString();
    }
}
