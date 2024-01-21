package com.vmware.service;

import com.vmware.model.Order;

public interface OrderService {
    void handlerOrder(Order order) throws InterruptedException;
}
