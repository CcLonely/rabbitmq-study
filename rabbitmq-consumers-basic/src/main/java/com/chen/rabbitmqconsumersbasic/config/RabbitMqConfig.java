package com.chen.rabbitmqconsumersbasic.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 白起老师
 */
@Configuration
public class RabbitMqConfig {

    //定义交换机的名字
    public static final String  EXCHANGE_NAME = "boot_topic_exchange";
    //定义队列的名字
    public static final String QUEUE_NAME = "boot_queue";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //1、声明交换机
    @Bean("bootExchange")
    public Exchange bootExchange(){

        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }



    //2、声明队列
    @Bean("bootQueue")
    public Queue bootQueue(){

        return QueueBuilder.durable(QUEUE_NAME).build();
    }


    //3、队列与交换机进行绑定
    @Bean
    public Binding bindQueueExchange(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

    @Bean
    public MessageRecoverer messageRecoverer(){
        return new ConsumerRejectAndDontRequeueRecoverer("测试");
    }


}
