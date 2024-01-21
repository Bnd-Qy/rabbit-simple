package com.vmware.controller;


import com.vmware.config.RabbitDeadConfig;
import com.vmware.config.RabbitDelayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/delay")
@RestController
public class DelayController {
    private final RabbitTemplate rabbitTemplate;

    public DelayController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/order/{orderId}")
    public String order(@PathVariable Long orderId) {
        log.info("收到订单:{}",orderId);
        rabbitTemplate.convertAndSend(RabbitDelayConfig.exchangeName, RabbitDelayConfig.routingKey, "订单:" + orderId);
        return "ok";
    }
}
