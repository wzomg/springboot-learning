package com.zzw.rabbitmq.direct;

import com.rabbitmq.client.*;
import com.zzw.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

public class Consumer {
    // junit 测试不支持多线程，所以测试消费者要在main主线成中运行
    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 获取连接中的通道对象
        Channel channel = connection.createChannel();
        // 通道绑定队列
        channel.queueDeclare("direct_1", false, false, false, null);

        // 消费者消费
        // 参数1：消费那个队列的消息队列名称
        // 参数2：开始消费的自动确认机制，false 不会自动确认消息
        // 参数3：消费时的回调接口
        channel.basicConsume("direct_1", true, new DefaultConsumer(channel) {
            // 最后一个参数：消息队列中取出的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("new String(body) = " + new String(body));
            }
        });
        // channel.close();
        // connection.close();
    }
}
