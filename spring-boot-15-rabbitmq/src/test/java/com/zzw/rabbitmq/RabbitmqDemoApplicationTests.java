package com.zzw.rabbitmq;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RabbitmqDemoApplication.class)
@RunWith(SpringRunner.class)
class RabbitmqDemoApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    // hello world
    @Test
    void testHelloWorld() {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }

    // work queue
    @Test
    void testWork() {
        // 公平消费
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "work模型 " + i);
        }
    }

    // fanout 广播
    @Test
    void testFanout() {
        rabbitTemplate.convertAndSend("logs", "", "fanout 模型发送的消息");
    }

    // route 路由模式
    @Test
    void testRoute() {
        rabbitTemplate.convertAndSend("directs", "error", "发送 info key 的路由信息");
    }


    // topic 路由模式
    @Test
    void testTopic() {
        rabbitTemplate.convertAndSend("topics", "produce.save.add", "produce.save.add 路由消息");
    }

}
