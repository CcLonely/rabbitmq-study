package com.chen.rabbitmqconsumersbasic.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.rabbitmqcommon.entity.MessageContent;
import com.chen.rabbitmqconsumersbasic.entity.RabbitMqErrorRetryLog;
import com.chen.rabbitmqconsumersbasic.mapper.RabbitMqErrorRetryLogMapper;
import com.chen.rabbitmqconsumersbasic.service.IRabbitMqErrorRetryLogService;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;


/**
 * <p>
 * 消息消费异常 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-07-20
 */
@Service
public class RabbitMqErrorRetryLogServiceImpl extends ServiceImpl<RabbitMqErrorRetryLogMapper, RabbitMqErrorRetryLog> implements IRabbitMqErrorRetryLogService {


    @Async
    @Override
    public void asyncExecuteLog(Message message, Boolean status) {
        MessageContent messageContent = new Gson().fromJson(new String(message.getBody()),MessageContent.class);
        /**
         * 异常不重试情况: 如果为首次 并且处理状态为成功不进行处理,
         * 异常重试情况
         **/

            //首次处理，并且状态为成功 不进行记录
            if (messageContent.getFirstTreatment() && status){
                return;
            }
            RabbitMqErrorRetryLog rabbitMqErrorRetryLog = getById(messageContent.getMessageId());
            if (ObjectUtils.isEmpty(rabbitMqErrorRetryLog)) {
                rabbitMqErrorRetryLog = assembleRabbitLog(message, messageContent);
                saveOrUpdate(rabbitMqErrorRetryLog);
                return;
            }
            //正常不会出现，毕竟要万无一失
            if (rabbitMqErrorRetryLog.getRetryStatus()){
                return;
            }
            //异常重试进行修改
             UpdateWrapper<RabbitMqErrorRetryLog> updateWrapper = new UpdateWrapper<>();
             updateWrapper.lambda().eq(RabbitMqErrorRetryLog::getMessageId,rabbitMqErrorRetryLog.getMessageId())
                     .eq(RabbitMqErrorRetryLog::getVersion,rabbitMqErrorRetryLog.getVersion());
             rabbitMqErrorRetryLog.setRetryCount(rabbitMqErrorRetryLog.getRetryCount()+1);
             rabbitMqErrorRetryLog.setVersion(rabbitMqErrorRetryLog.getVersion()+1);
             rabbitMqErrorRetryLog.setRetryStatus(status);
             rabbitMqErrorRetryLog.setUpdateByUser("SYSTEM");
             rabbitMqErrorRetryLog.setUpdateTime(new Date());
             rabbitMqErrorRetryLog.setProcessStatus(true);
             update(rabbitMqErrorRetryLog,updateWrapper);

    }

    /**
     * @Author chengeng
     * @Description 组装rabbitmq错误日志信息
     * @Date 9:17 2021/7/21
     * @Param [message]
     * @return com.chen.rabbitmqconsumersbasic.entity.RabbitMqErrorRetryLog
     **/
    private RabbitMqErrorRetryLog assembleRabbitLog(Message message,MessageContent messageContent){

        RabbitMqErrorRetryLog rabbitMqErrorRetryLog = new RabbitMqErrorRetryLog();
        //设置业务内容
        rabbitMqErrorRetryLog.setMessageId(messageContent.getMessageId());
        rabbitMqErrorRetryLog.setBusinessLine(messageContent.getBusinessLine());
        rabbitMqErrorRetryLog.setMessageContent(messageContent.getMessageContent());
        rabbitMqErrorRetryLog.setRetryCount(0);
        rabbitMqErrorRetryLog.setRetryStatus(false);
        rabbitMqErrorRetryLog.setVersion(0L);
        rabbitMqErrorRetryLog.setProcessStatus(true);
        //设置队列内容
        rabbitMqErrorRetryLog.setMqExchange(message.getMessageProperties().getReceivedExchange());
        rabbitMqErrorRetryLog.setMqQueue(message.getMessageProperties().getConsumerQueue());
        rabbitMqErrorRetryLog.setMqRoutingKey(message.getMessageProperties().getReceivedRoutingKey());
        //设置创建修改信息
        rabbitMqErrorRetryLog.setCreateTime(new Date());
        rabbitMqErrorRetryLog.setCreateByUser("SYSTEM");
        return rabbitMqErrorRetryLog;
    }
}
