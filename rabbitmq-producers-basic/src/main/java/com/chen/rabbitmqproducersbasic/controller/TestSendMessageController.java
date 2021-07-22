package com.chen.rabbitmqproducersbasic.controller;

import com.chen.rabbitmqproducersbasic.producer.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cg
 * @version 1.0
 * @date 2021/7/20 14:07
 */
@RestController
public class TestSendMessageController {

    @Autowired
    private RabbitSender rabbitSender;

    @RequestMapping(value = "/test")
    public String send(String param){
        rabbitSender.sendString(param);
        return "true";
    }
}
