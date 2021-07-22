package com.chen.rabbitmqconsumersbasic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息消费异常
 * </p>
 *
 * @author chen
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "rabbit_mq_error_retry_log")
public class RabbitMqErrorRetryLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,消息ID
     */
      @TableId(type = IdType.NONE)
      private String messageId;

    /**
     * 所属业务线
     */
    private String businessLine;

    /**
     * 业务内容
     */
    private String messageContent;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 备注  运营处理备注
     */
    private String remark;

    /**
     * 重试处理状态
     */
    private Boolean retryStatus;

    /**
     * 处理状态 消息投递使用
     */
    private Boolean processStatus;

    /**
     * 交换机
     */
    private String mqExchange;

    /**
     * 路由key
     */
    private String mqRoutingKey;

    /**
     * 所属队列
     */
    private String mqQueue;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 创建人
     */
    private String createByUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateByUser;

    /**
     * 修改时间
     */
    private Date updateTime;


}
