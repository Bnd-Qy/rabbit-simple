package com.vmware.service.impl;

import com.vmware.model.Order;
import com.vmware.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public void handlerOrder(Order order) throws InterruptedException {
//        for (int i = 0; i < 3; i++) {
//            log.info("系统处理订单:{}中...",order.getId());
//            TimeUnit.SECONDS.sleep(1);
//        }
        TimeUnit.SECONDS.sleep(3);
        log.info("订单:{} 处理完成",order.getId());
    }
}
