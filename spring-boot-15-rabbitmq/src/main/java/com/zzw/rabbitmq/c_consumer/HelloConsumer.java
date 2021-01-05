package com.zzw.rabbitmq.c_consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 持久化 非独占 不是自动删除队列
@Component
// 没有队列则去创建队列
@RabbitListener(queuesToDeclare = @Queue(value = "hello"))
public class HelloConsumer {

    // 指定回调方法
    @RabbitHandler
    public void receive1(String message) {
        System.out.println("message = " + message);
    }

}
