package com.chen.rabbitmqproducersbasic.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.rabbitmqcommon.entity.MessageContent;
import com.chen.rabbitmqproducersbasic.entity.RabbitMqErrorRetryLog;
import com.chen.rabbitmqproducersbasic.service.IRabbitMqErrorRetryLogService;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author cg
 * @version 1.0
 * @date 2021/7/22 13:56
 */
@Component
@EnableScheduling
public class RabbitMqRetry {

    @Autowired
    private IRabbitMqErrorRetryLogService iRabbitMqErrorRetryLogService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void rabbitmq(){

        System.out.println("异常重试");
        QueryWrapper<RabbitMqErrorRetryLog> queryWrapper = new QueryWrapper();

        queryWrapper.lambda().
                eq(RabbitMqErrorRetryLog::getRetryStatus,false)
                .eq(RabbitMqErrorRetryLog::getProcessStatus,true)
                .lt(RabbitMqErrorRetryLog::getRetryCount,5L);
        List<RabbitMqErrorRetryLog> retryLogs = iRabbitMqErrorRetryLogService.list(queryWrapper);
        if (!CollectionUtils.isEmpty(retryLogs)){
            retryLogs.forEach(item -> {

                MessageContent messageContent = new MessageContent(item.getBusinessLine(),item.getMessageContent());
                messageContent.setMessageId(item.getMessageId());
                messageContent.setFirstTreatment(false);



                String messageStr = new Gson().toJson(messageContent);
                Message message = new Message(messageStr.getBytes());
                //每次都会重发失败的，无法确定消息是否处理过，发布时修改为状态未处理，下次未消费就不会进行重发，客户端处理时进行修改此时处理失败就会处理修改
                //如果是微服务，不推荐可能会重复投递
                rabbitTemplate.send(item.getMqExchange(),item.getMqRoutingKey(),message);


                //修改为状态未处理，避免下次消息重发
                item.setProcessStatus(false);
                iRabbitMqErrorRetryLogService.updateById(item);
            });
        }
    }
}
