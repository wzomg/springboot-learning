package com.zzw.springboot10amqp.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAMQPConfig {

    @Bean
    public MessageConverter messageConverter() {
        // 只需要new一个json对象即可，会加载我们自定义的转换方案
        return new Jackson2JsonMessageConverter();
    }
}
