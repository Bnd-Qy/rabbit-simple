package com.vmware.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
public class DeadListener {
    @RabbitListener(queues = "dead_queue")
    public void deadListener(Message message, Channel channel) throws IOException {
        log.info("死信队列收到消息:{}",message);
        long tag = message.getMessageProperties().getDeliveryTag();
        channel.basicNack(tag,false,false);
    }
}
