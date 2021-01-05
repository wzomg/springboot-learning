package com.zzw.rabbitmq.topic;

import com.rabbitmq.client.*;
import com.zzw.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

public class Consumer1 {
    // junit 测试不支持多线程，所以测试消费者要在main主线成中运行
    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();

        String exchangeName = "topics";

        // 获取连接中的通道对象
        Channel channel = connection.createChannel();

        // 通道声明交换机以及交换的类型
        channel.exchangeDeclare(exchangeName, "topic");

        // 创建一个临时队列
        String queueName = channel.queueDeclare().getQueue();

        // 基于动态通配符形式的 route key 绑定队列和交换机
        channel.queueBind(queueName, exchangeName, "user.*");

        // 消费消息
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1：" + new String(body));
            }
        });
    }
}
