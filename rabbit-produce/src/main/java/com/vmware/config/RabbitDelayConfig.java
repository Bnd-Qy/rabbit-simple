package com.vmware.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 延迟队列的实现:普通队列设置过期+死信队列
 */
@Configuration
public class RabbitDelayConfig {
    public static final String queueName = "Order_Queue";
    public static final String deadQueueName = "Order_Dead_Queue";
    public static final String deadExchangeName = "Order_Dead_Exchange";
    public static final String exchangeName = "Order_Exchange";
    public static final String routingKey = "Order_Router";
    public static final String deadRoutingKey = "Order_Dead_Router";

    /**
     * @return
     * @apiNote 声明普通队列过期时间为30s
     */
    @Bean("orderQueue")
    public Queue queue() {
        return QueueBuilder
                .durable(queueName)
                .ttl(10000)
                .deadLetterExchange(deadExchangeName)
                .deadLetterRoutingKey(deadRoutingKey)
                .build();
    }

    @Bean("orderExchange")
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(exchangeName)
                .durable(true)
                .build();
    }

    /**
     * 绑定普通交换机与普通队列
     * @param exchange
     * @param queue
     * @return
     */
    @Bean
    public Binding binding(@Qualifier("orderExchange") Exchange exchange, @Qualifier("orderQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange)
                .with(routingKey)
                .noargs();
    }

    @Bean
    public Queue deadQueue(){
        return QueueBuilder.durable(deadQueueName)
                .build();
    }

    @Bean
    public Exchange deadExchange(){
        return ExchangeBuilder.directExchange(deadExchangeName)
                .durable(true)
                .build();
    }

    /**
     * @apiNote 绑定死信交换机和死信队列
     * @param exchange
     * @param queue
     * @return
     */
    @Bean
    public Binding deadBinding(@Qualifier("deadExchange") Exchange exchange,@Qualifier("deadQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(deadRoutingKey).noargs();
    }
}
