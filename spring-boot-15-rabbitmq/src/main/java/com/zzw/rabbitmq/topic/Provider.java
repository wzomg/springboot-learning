package com.zzw.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zzw.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

public class Provider {
    // junit 测试不支持多线程，所以测试消费者要在main主线成中运行
    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();

        String exchangeName = "topics";

        // 获取连接中的通道对象
        Channel channel = connection.createChannel();

        // 通道声明指定的交换机
        // 参数1：交换机的名称
        // 参数2：交换机的类型，fanout：广播类型， topic：订阅与发布
        channel.exchangeDeclare(exchangeName, "topic");

        // 发送消息
        String routingKey = "user.save.findAll";
        channel.basicPublish(exchangeName, routingKey, null, ("这是 topic 动态模型发布的基于 route key：[" + routingKey + "] 发送的消息。").getBytes());

        // 释放资源
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }
}
