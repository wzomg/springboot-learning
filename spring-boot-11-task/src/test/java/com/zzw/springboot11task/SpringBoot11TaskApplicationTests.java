package com.zzw.springboot11task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@SpringBootTest
class SpringBoot11TaskApplicationTests {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件设置
        message.setSubject("通知-今晚开会"); // 设置邮件标题
        message.setText("今晚7：30开会"); // 设置邮件内容
        message.setTo("接收方邮箱地址");
        message.setFrom("发送方邮箱地址");
        mailSender.send(message);
    }
}
