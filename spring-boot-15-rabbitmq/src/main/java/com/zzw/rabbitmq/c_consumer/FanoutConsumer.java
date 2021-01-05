package com.zzw.rabbitmq.c_consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    // 注意 默认持久化值为 true
                    value = @Queue, // 创建临时队列
                    exchange = @Exchange(value = "logs", type = "fanout", durable = "false")    // 绑定的交换机
            )
    })
    public void receive1(String message) {
        System.out.println("message1 = " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue, // 创建临时队列
                    exchange = @Exchange(value = "logs", type = "fanout", durable = "false")    // 绑定的交换机
            )
    })
    public void receive2(String message) {
        System.out.println("message2 = " + message);
    }
}