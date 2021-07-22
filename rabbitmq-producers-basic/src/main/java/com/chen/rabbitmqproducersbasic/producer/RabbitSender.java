package com.chen.rabbitmqproducersbasic.producer;

import com.chen.rabbitmqcommon.entity.MessageContent;
import com.chen.rabbitmqproducersbasic.config.RabbitMqConfig;
import com.chen.rabbitmqproducersbasic.entity.Order;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cg
 * @version 1.0
 * @date 2021/7/20 13:58
 */
@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;



    public Boolean sendString(String message){
        Order order = new Order();
        order.setOrderId(message);
        MessageContent messageContent = new MessageContent("HUICHUXING",new Gson().toJson(order));


        String messageStr = new Gson().toJson(messageContent);
        Message message1 = new Message(messageStr.getBytes());
        rabbitTemplate.convertAndSend(RabbitMqConfig.QUEUE_NAME, message1);
        return true;
    }
}
