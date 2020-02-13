package com.zzw.springboot10amqp;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 自动配置：RabbitAutoConfiguration
 * 1、有自动配置的连接工厂：CachingConnectionFactory
 * 2、RabbitProperties 封装了rabbitmq的配置
 * 3、RabbitTemplate：给 Rabbitmq 发送和接收消息；
 * 4、AmqpAdmin ：Rabbitmq 系统管理功能组件
 *      创建和删除 Queue、Exchange、Binding
 * 5、@RabbitListener + @EnableRabbit 监听消息队列的内容
 */
@EnableRabbit //开启基于注解的 RabbitMQ 模式
@SpringBootApplication
public class SpringBoot10AmqpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot10AmqpApplication.class, args);
    }

}
