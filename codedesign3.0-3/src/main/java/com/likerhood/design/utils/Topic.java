package com.likerhood.design.utils;

import java.util.Map;

public class Topic {

    private Map<String, String> option;  // 选项abcd、
    private String key;        // 答案

    public Topic(Map<String, String> option, String key) {
        this.option = option;
        this.key = key;
    }

    public Topic() {
    }

    public Map<String, String> getOption() {
        return option;
    }

    public void setOption(Map<String, String> option) {
        this.option = option;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
