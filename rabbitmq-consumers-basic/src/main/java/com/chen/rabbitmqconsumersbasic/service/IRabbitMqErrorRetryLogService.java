package com.chen.rabbitmqconsumersbasic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.rabbitmqconsumersbasic.entity.RabbitMqErrorRetryLog;
import org.springframework.amqp.core.Message;

/**
 * <p>
 * 消息消费异常 服务类
 * </p>
 *
 * @author chen
 * @since 2021-07-20
 */
public interface IRabbitMqErrorRetryLogService extends IService<RabbitMqErrorRetryLog> {

    /**
     * @Author chengeng
     * @Description 异步处理消息日志
     * @Date 11:10 2021/7/21
     * @Param [message, status]
     * @return void
     **/
    void asyncExecuteLog(Message message,Boolean status);
}
