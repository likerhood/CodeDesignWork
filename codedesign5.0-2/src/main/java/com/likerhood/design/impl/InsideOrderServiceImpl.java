package com.likerhood.design.impl;


import com.likerhood.design.OrderAdapterService;
import com.likerhood.design.service.OrderService;

/**
 * 内部订单，判断首单逻辑
 */
public class InsideOrderServiceImpl implements OrderAdapterService {

    private OrderService orderService = new OrderService();

    public boolean isFirst(String uId) {
        return orderService.queryUserOrderCount(uId) <= 1;
    }
}
