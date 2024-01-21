package com.vmware.controller;

import com.vmware.constant.RabbitConstant;
import com.vmware.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/handler/{orderId}")
    public String handlerOrder(@PathVariable Long orderId) {
        Order order = new Order(orderId);
        log.info("接受到新订单,等待处理...{}", order);
        /**
         * confirm callback配置：1.配置文件开启publisher-confirm-type 2.设置回调方法ConfirmCallback
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("confirm方法执行了");
            if (ack) {
                log.info("消息投递成功!");
            } else {
                log.info("消息投递失败! {}", cause);
            }
        });
        /**
         * 设置return callback:1.配置文件开启publisher-returns: true 2.配置mandatory为true 3.配置return callback
         */
        rabbitTemplate.setMandatory(true);//true:消息投递失败时返回给发送者 false:消息发送失败时丢弃消息
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息投递至队列失败.. message:{} error code:{} exchange:{} routing_key:{}",message,replyCode,exchange,routingKey);
        });
        rabbitTemplate.convertAndSend(RabbitConstant.EXCHANGE_NAME, RabbitConstant.DEFAULT_ROUTING_KEY, order);
        return "200";
    }

    /**
     * @apiNote 批量生成订单供消费方消费
     * @return
     */
    @GetMapping("/multi")
    public String multiPublishOrder(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(RabbitConstant.EXCHANGE_NAME, RabbitConstant.DEFAULT_ROUTING_KEY, new Order((long) i));
        }
        return "ok";
    }
}
