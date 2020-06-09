package com.zzw.swagger.controller;

import com.zzw.swagger.pojo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    // 只要接口中返回值存在实体类，其就会被扫描到 swagger 文档中
    @PostMapping(value = "/user")
    public User user() {
        return new User();
    }

    // @ApiOperation接口，不是放在类上，而是放在方法上
    @ApiOperation("csr控制类")
    @PostMapping(value = "/postUsername")
    public String csr(@ApiParam("用户名") String username) {
        return "hello " + username;
    }

    // 注意实体类要提供 get 和 set 方法
    @ApiOperation("用户注册控制类")
    @PostMapping(value = "/postUser")
    public User insertUser(@ApiParam("用户名") User user) {
        return  user;
    }
}
