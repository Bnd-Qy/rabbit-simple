package com.vmware.controller;

import com.vmware.constant.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/publish")
@RestController
public class PublishController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/{message}")
    public String publish(@PathVariable String message){
        try {
            log.info("发送消息到消息队列:{}",message);
            rabbitTemplate.convertAndSend(RabbitConstant.EXCHANGE_NAME,RabbitConstant.DEFAULT_ROUTING_KEY,message);
            return "publish success!";
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return "publish failed";
        }
    }
}
