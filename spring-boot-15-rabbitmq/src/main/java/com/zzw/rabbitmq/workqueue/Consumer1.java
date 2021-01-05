package com.zzw.rabbitmq.workqueue;

import com.rabbitmq.client.*;
import com.zzw.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

public class Consumer1 {
    // junit 测试不支持多线程，所以测试消费者要在main主线成中运行
    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 获取连接中的通道对象
        Channel channel = connection.createChannel();
        // 每次只接收一条为确认的消息
        channel.basicQos(1);
        // 通道绑定队列
        channel.queueDeclare("work_1", true, false, false, null);

        // 消费者消费
        // 参数1：消费那个队列的消息队列名称
        // 参数2：开始消费的自动确认机制，false 不会自动确认消息机制
        // 参数3：消费时的回调接口
        channel.basicConsume("work_1", false, new DefaultConsumer(channel) {
            // 最后一个参数：消息队列中取出的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者-1：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
        // channel.close();
        // connection.close();
    }
}
