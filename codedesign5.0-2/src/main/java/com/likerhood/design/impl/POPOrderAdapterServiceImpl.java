package com.likerhood.design.impl;

import com.likerhood.design.OrderAdapterService;
import com.likerhood.design.service.POPOrderService;

public class POPOrderAdapterServiceImpl implements OrderAdapterService {
    private POPOrderService popOrderService = new POPOrderService();

    public boolean isFirst(String uId) {
        return popOrderService.isFirstOrder(uId);
    }
}
