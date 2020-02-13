package com.zzw.springboot10amqp.service;

import com.zzw.springboot10amqp.pojo.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @RabbitListener(queues = "zzw.news")
    public void receive(Book book) {
        //只要队列：zzw.news一有消息，就会执行此方法
        System.out.println("收到消息：" + book);
    }

    @RabbitListener(queues = "zzw")
    public void receive2(Message message) {
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
