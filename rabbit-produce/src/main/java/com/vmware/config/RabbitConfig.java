package com.vmware.config;

import com.vmware.constant.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class RabbitConfig {
    /**
     * 声明交换机
     *
     * @return 交换机
     */
    @Bean("directExchange")
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(RabbitConstant.EXCHANGE_NAME).durable(true)
                .build();
    }

    @Bean("queue1")
    public Queue queue() {
        return QueueBuilder.nonDurable(RabbitConstant.QUEUE_NAME).build();
    }


    @Bean("bindingDirect")
    public Binding binding(@Qualifier("directExchange") Exchange exchange, @Qualifier("queue1") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitConstant.DEFAULT_ROUTING_KEY).noargs();
    }

    @Bean
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(RabbitConstant.TOPIC_EXCHANGE).durable(true).build();
    }

    /**
     * @apiNote 设置队列过期时间,消息过期后会批量删除
     * @return
     */
    @Bean
    public Queue ttlQueue(){
        //ttl方法内部调用了:withArgument("x-message-ttl", ttl);
        return QueueBuilder.durable(RabbitConstant.TTL_QUEUE).ttl(5000).build();
    }

    @Bean
    public Binding ttlBinding(@Qualifier("topicExchange") Exchange exchange,@Qualifier("ttlQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(RabbitConstant.TTL_ROUTING_KEY).noargs();
    }

    @Bean
    public Queue ttlQueue2(){
        return QueueBuilder.durable(RabbitConstant.TTL_QUEUE2).build();
    }

    @Bean
    public Binding ttl2Binding(@Qualifier("topicExchange") Exchange exchange,@Qualifier("ttlQueue2") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(RabbitConstant.TTL_ROUTING_KEY2).noargs();
    }
}
