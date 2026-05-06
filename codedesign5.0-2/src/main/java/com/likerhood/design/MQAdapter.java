package com.likerhood.design;

import com.alibaba.fastjson.JSON;

import java.util.Map;

public class MQAdapter {

    // 入口1：接收 JSON 字符串，先转成 Map 再调入口2
    public static RebateInfo filter(String strJson, Map<String, String> link) throws Exception {

        return filter(JSON.parseObject(strJson, Map.class), link);
    }

    // 入口2：接收已解析的 Map，执行真正的字段映射
    public static RebateInfo filter(Map obj, Map<String, String> link) throws Exception {

        RebateInfo rebateInfo = new RebateInfo();
        for (String key : link.keySet()) {
            Object val = obj.get(link.get(key));
            RebateInfo.class.getMethod("set" + key.substring(0, 1).toUpperCase() + key.substring(1), String.class).invoke(rebateInfo, val.toString());
        }

        return rebateInfo;
    }
}
