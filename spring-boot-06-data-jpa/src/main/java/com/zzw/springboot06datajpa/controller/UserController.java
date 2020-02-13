package com.zzw.springboot06datajpa.controller;

import com.zzw.springboot06datajpa.entity.User;
import com.zzw.springboot06datajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // url：http://localhost:8080/user/1
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Integer id){
        return userRepository.getOne(id);
    }

    //1、http://localhost:8080/user?lastName=zhangsan&email=123456@qq.com
    //2、http://localhost:8080/user?lastName=lisi&email=78215646@qq.com
    @GetMapping("/user")
    public User insertUser(User user){
        return userRepository.save(user);
    }

}
