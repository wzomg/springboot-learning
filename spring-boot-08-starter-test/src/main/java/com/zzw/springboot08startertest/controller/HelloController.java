package com.zzw.springboot08startertest.controller;

import com.zzw.starter.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    // url：http://localhost:8080/hello
    @GetMapping("/hello")
    public String hello() {
        // 在配置文件中配置自定义的前后缀
        return helloService.sayHelloZzw("haha");
    }
}
