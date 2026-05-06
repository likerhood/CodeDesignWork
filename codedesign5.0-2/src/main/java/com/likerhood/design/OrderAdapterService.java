package com.likerhood.design;


import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

/**
 * 字段映射适配器，核心职责是：把任意 MQ 的 JSON 字符串，按照调用方提供的映射规则，转换成统一的 RebateInfo 对象。
 */
public interface OrderAdapterService {

    boolean isFirst(String uId);

}
