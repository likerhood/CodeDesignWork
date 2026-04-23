package com.likerhood.design.utils;

import java.util.*;

public class TopicRandomUtil {

    /**
     * 乱序Map元素，记录对应答案key
     * @param option 题目
     * @param key    答案
     * @return Topic 乱序后 {A=c., B=d., C=a., D=b.}
     */
    static public Topic random(Map<String, String> option, String key){

        Set<String> keySet = option.keySet();
        // 用一个列表来存储这个选项
        ArrayList<String> keyList = new ArrayList<>(keySet);
        // 将列表乱序
        Collections.shuffle(keyList);
        // 一个新的map来存储乱序之后的答案和选项
        HashMap<String, String> optionNew = new HashMap<String, String>();
        // 遍历keyList的下标
        int idx = 0;
        String answerNew = "";
        for (String originKey : keySet) {
            String newKey = keyList.get(idx++);
            if (key.equals(originKey)){
                answerNew = newKey;
            }
            // 加入到新选项
            optionNew.put(newKey, option.get(originKey));
        }


        return new Topic(optionNew, answerNew);

    }


}
