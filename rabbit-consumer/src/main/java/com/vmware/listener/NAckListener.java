package com.vmware.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
//@Component
public class NAckListener {
    @RabbitListener(queues = {"queue1"})
    public void listener(Channel channel, Message message) throws IOException {
        //获取tag
        long tag = message.getMessageProperties().getDeliveryTag();
        log.info("拒绝消息:{}",message);
        channel.basicNack(tag,false,false);//让消息进入死信队列必须要拒绝并且取消重入队列
    }
}
