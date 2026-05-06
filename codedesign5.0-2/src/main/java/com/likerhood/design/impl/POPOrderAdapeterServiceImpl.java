package com.likerhood.design.impl;

import com.likerhood.design.service.POPOrderService;

public class POPOrderAdapeterServiceImpl {
    private POPOrderService popOrderService = new POPOrderService();

    public boolean isFirst(String uId) {
        return popOrderService.isFirstOrder(uId);
    }
}
