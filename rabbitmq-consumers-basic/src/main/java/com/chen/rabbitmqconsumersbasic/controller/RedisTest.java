package com.chen.rabbitmqconsumersbasic.controller;

import com.chen.rabbitmqconsumersbasic.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author cg
 * @version 1.0
 * @date 2021/7/19 10:48
 */
@Controller
public class RedisTest {

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(){
        redisUtil.set("test-key2","test-value");
        return "true";
    }
}
