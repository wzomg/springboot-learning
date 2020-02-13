package com.zzw.springboot.controller;

import com.zzw.springboot.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;


@Controller
public class HelloController {

    //没有必要单独写一个空方法来实现页面的跳转
    /*@RequestMapping({"/", "/login.html"})
    public String index() {
        //默认去寻找前缀为classpath:/templates/，名为index.html文件
        return "index";
    }*/

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(@RequestParam("user") String user) {
        if(user.equals("aaa")) {
            // 抛了一个自定义异常
            throw new UserNotExistException();
        }
        return "Hello Spring-boot!";
    }


    //查出一些数据，在页面展示
    @RequestMapping("/csGo")
    public String go(Map<String, Object> map) {
        map.put("hello", "<h1>你好，世界！</h1>");
        map.put("users", Arrays.asList("张三", "李四", "王五"));
        //访问路径：http://localhost:8080/csGo
        //自动去访问类资源路径下的某个文件：classpath:/templates/success.html
        return "success";
    }
}


