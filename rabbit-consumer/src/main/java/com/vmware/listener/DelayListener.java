package com.vmware.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听延迟队列
 */
@Slf4j
@Component
public class DelayListener {
    @RabbitListener(queues = "Order_Dead_Queue")
    public void listenerOrder(Message message){
        log.info("检查订单状态中...");
        log.info("订单取消...");
    }
}
