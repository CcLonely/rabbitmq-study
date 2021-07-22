package com.chen.rabbitmqcommon.entity;

import java.util.UUID;

/**
 * @author cg
 * @version 1.0
 * @date 2021/7/21 9:30
 * 消息内容
 */
public class MessageContent {

    /**
     * 消息ID
     **/
    private String messageId;

    /**
     * 所属业务线 记录日志使用
     **/
    private String businessLine;

    /**
     * 首次处理 默认 True
     **/
    private Boolean firstTreatment;

    /**
     * 业务消息内容 业务处理使用
     **/
    private String messageContent;

    public MessageContent(){
        this.messageId = UUID.randomUUID().toString().replaceAll("-","");
        this.firstTreatment = true;
    }

    public MessageContent(String messageContent){
        this.messageId = UUID.randomUUID().toString().replaceAll("-","");
        this.messageContent = messageContent;
        this.firstTreatment = true;
    }

    public MessageContent(String businessLine,String messageContent){
        this.messageId = UUID.randomUUID().toString().replaceAll("-","");
        this.businessLine = businessLine;
        this.messageContent = messageContent;
        this.firstTreatment = true;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Boolean getFirstTreatment() {
        return firstTreatment;
    }

    public void setFirstTreatment(Boolean firstTreatment) {
        this.firstTreatment = firstTreatment;
    }
}
