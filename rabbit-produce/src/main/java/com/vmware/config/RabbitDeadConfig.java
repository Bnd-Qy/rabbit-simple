package com.vmware.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RabbitDeadConfig {
    public static final String exchangeName = "exchange1";
    public static final String queueName = "queue1";
    public static final String routingKey = "simpleKey";
    public static final String deadExchangeName = "dead_exchange";
    public static final String deadQueueName = "dead_queue";
    public static final String deadRoutingKey = "dead_key";
    /**
     * 声明正常交换机
     */
    @Bean("exchange")
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(exchangeName).durable(true)
                .build();
    }

    /**
     * 声明正常队列，并绑定死信交换机
     * @return
     */
    @Bean("queue")
    public Queue queue() {
        return QueueBuilder.durable(queueName)
                .ttl(10000)//过期或者队列满了之后消息都会进入死信队列
                .maxLength(10)
                .deadLetterExchange(deadExchangeName)
                .deadLetterRoutingKey(deadRoutingKey)
                .build();
    }

    @Bean
    public Binding binding(@Qualifier("exchange") Exchange exchange, @Qualifier("queue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }

    /**
     * 声明私信交换机
     * @return
     */
    @Bean("deadExchange")
    public Exchange deadExchange() {
        return ExchangeBuilder.directExchange(deadExchangeName).durable(true)
                .build();
    }

    @Bean("deadQueue")
    public Queue deadQueue(){
        return QueueBuilder.durable(deadQueueName).build();
    }

    @Bean
    public Binding bindingDead(@Qualifier("deadExchange") Exchange exchange, @Qualifier("deadQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(deadRoutingKey).noargs();
    }
}
