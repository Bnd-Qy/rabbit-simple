package com.vmware.listener;

import com.rabbitmq.client.Channel;
import com.vmware.constant.RabbitConstant;
import com.vmware.model.Order;
import com.vmware.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
//@Component
public class OrderListener{
//    @Autowired
    OrderService orderService;

    @RabbitListener(queues = RabbitConstant.QUEUE_NAME)
    public void orderListener(Channel channel,Message message, Order order) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();//获取消息的标记
        log.info("收到订单:{}",order);
        try {
            orderService.handlerOrder(order);
            channel.basicAck(deliveryTag,false);//确认接收，multiple:是否批量应答
        } catch (Exception e) {
            try {
                channel.basicNack(deliveryTag,false,true);//参数2:批量应答? 参数3:重新进入队列?
            } catch (IOException ex) {
                log.error(ex.getMessage(),ex);
                throw new RuntimeException(ex);
            }
            log.error("订单:{}处理失败",e.getMessage());
        }
    }
}
