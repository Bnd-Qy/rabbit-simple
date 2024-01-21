package com.vmware.listener;

import com.vmware.constant.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleListener {
//    @RabbitListener(queues = RabbitConstant.QUEUE_NAME)
//    public void listener(Message message) {
//        log.info("消费端收到消息:{}", message);
//    }
}
