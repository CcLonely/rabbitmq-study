package com.chen.rabbitmqconsumersbasic.config;

import com.chen.rabbitmqconsumersbasic.service.IRabbitMqErrorRetryLogService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author cg
 * @version 1.0
 * @date 2021/7/23 9:18
 *
 * 自定义默认实现，所有重试失败新增重试结果，或增加重试次数
 */
@Component
public class ConsumerRejectAndDontRequeueRecoverer extends RejectAndDontRequeueRecoverer {

    @Autowired
    private IRabbitMqErrorRetryLogService iRabbitMqErrorRetryLogService;

    public ConsumerRejectAndDontRequeueRecoverer() {
        super();
    }

    public ConsumerRejectAndDontRequeueRecoverer(String message) {
        super(message);
    }

    public ConsumerRejectAndDontRequeueRecoverer(Supplier<String> messageSupplier) {
        super(messageSupplier);
    }




    /**
     * @Author chengeng
     * @Description 最后一次重试失败方法执行
     * @Date 9:41 2021/7/23
     * @Param [message, cause]
     * @return void
     **/
    @Override
    public void recover(Message message, Throwable cause) {
        iRabbitMqErrorRetryLogService.asyncExecuteLog(message,false);
        super.recover(message, cause);
    }
}
