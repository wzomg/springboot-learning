package com.zzw.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.zzw.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

// 平均分配任务给消费者
public class Provider {
    // 生产消息
    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 获取连接中的通道对象
        Channel channel = connection.createChannel();
        // 通道绑定对应消息队列（队列声明）
        // 参数1：队列名称，若队列不存在则自动创建
        // 参数2：用来定义队列特性是否需要持久化，true表示持久化队列
        // 参数3：exclusive：是否独占队列，true 表示独占队列（只能被当前通道绑定）
        // 参数4：autoDelete：是否在消费完成后自动删除队列，true 则表示消费完之后是否要自动删除队列，false 不自动删除
        // 参数5：额外的参数
        channel.queueDeclare("work_1", true, false, false, null);

        // 发布消息
        // 参数1：交换机名称
        // 参数2：队列名称
        // 参数3：传递消息的额外设置：MessageProperties.PERSISTENT_TEXT_PLAIN
        //                      （告诉队列中的消息，即使在rabbitmq重启服务之后也要持久化到当前的消息队列中）
        // 参数4：消息的具体内容
        for (int i = 0; i < 20; i++) {
            channel.basicPublish("", "work_1", MessageProperties.PERSISTENT_TEXT_PLAIN, (i + " hello work queue!").getBytes());
        }
        // 关闭通道和连接
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }
}
