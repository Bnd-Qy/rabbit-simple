package com.vmware.controller;

import com.vmware.config.RabbitConfig;
import com.vmware.config.RabbitDeadConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于测试死信队列
 */
@RequestMapping("/dead")
@RestController
public class DeadController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send/{num}")
    public String send(@PathVariable Integer num) {
        for (int i = 0; i < num; i++) {
            rabbitTemplate.convertAndSend(RabbitDeadConfig.exchangeName, RabbitDeadConfig.routingKey, "hello:" + i);
        }
        return "ok";
    }
}
