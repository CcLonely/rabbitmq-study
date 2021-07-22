package com.chen.rabbitmqconsumersbasic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan(basePackages = {"com.chen.rabbitmqconsumersbasic.mapper"}) //扫描DA
@EnableAsync
public class RabbitmqConsumersBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqConsumersBasicApplication.class, args);
    }

}
