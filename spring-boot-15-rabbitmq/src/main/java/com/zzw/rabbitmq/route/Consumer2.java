package com.zzw.rabbitmq.route;

import com.rabbitmq.client.*;
import com.zzw.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

public class Consumer2 {
    // junit 测试不支持多线程，所以测试消费者要在main主线成中运行
    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 获取连接中的通道对象
        Channel channel = connection.createChannel();

        String exchangeName = "logs_direct";

        // 声明交换机 以及交换机类型 direct
        channel.exchangeDeclare(exchangeName, "direct");

        // 临时队列
        String queueName = channel.queueDeclare().getQueue();

        // 绑定交换机和队列
        channel.queueBind(queueName, exchangeName, "info");
        channel.queueBind(queueName, exchangeName, "error");
        channel.queueBind(queueName, exchangeName, "warning");

        // 消费消息
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2：" + new String(body));
            }
        });
    }
}
