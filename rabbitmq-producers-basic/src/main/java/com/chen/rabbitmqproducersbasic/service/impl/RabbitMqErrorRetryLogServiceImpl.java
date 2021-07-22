package com.chen.rabbitmqproducersbasic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.rabbitmqproducersbasic.entity.RabbitMqErrorRetryLog;
import com.chen.rabbitmqproducersbasic.mapper.RabbitMqErrorRetryLogMapper;
import com.chen.rabbitmqproducersbasic.service.IRabbitMqErrorRetryLogService;
import org.springframework.stereotype.Service;



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



}
