package com.chen.rabbitmqproducersbasic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.chen.rabbitmqproducersbasic.mapper"}) //扫描DA
public class RabbitmqProducersBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducersBasicApplication.class, args);
    }

}
