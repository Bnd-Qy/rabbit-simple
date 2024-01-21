package com.vmware.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class TTLListener {

    @RabbitListener(queues = "TTL_QUEUE")
    public void listener(Message message){
        log.info("收到消息:{}",message);
    }

    @RabbitListener(queues = "TTL_QUEUE2")
    public void listener2(Message message){
        log.info("TTL_QUEUE2收到消息:{}",message);
    }
}
