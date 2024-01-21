package com.vmware.controller;

import com.vmware.constant.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/ttl")
@RestController
public class TTLController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/send/{num}")
    public String send(@PathVariable Integer num){
        log.info("投递可过期消息{}条",num);
        for (int i = 0; i < num; i++) {
            rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGE,"ttl.test","测试:"+num);
        }
        return "ok";
    }

    @GetMapping("/send/q2/{message}")
    public String sendToQ2(@PathVariable String message){
        rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGE,"ttl",message,msg->{
            msg.getMessageProperties().setExpiration("3000");//设置单个消息过期时间为3秒
            return msg;
        });
        return "ok";
    }
}
