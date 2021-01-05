package com.zzw.rabbitmq.fanout;

import com.rabbitmq.client.*;
import com.zzw.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

public class Provider {
    // junit 测试不支持多线程，所以测试消费者要在main主线成中运行
    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 获取连接中的通道对象
        Channel channel = connection.createChannel();

        // 通道声明指定的交换机
        // 参数1：交换机的名称
        // 参数2：交换机的类型，fanout：广播类型
        channel.exchangeDeclare("logs", "fanout");

        // 发送消息
        channel.basicPublish("logs", "", null, "fanout type message".getBytes());

        // 释放资源
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }
}
