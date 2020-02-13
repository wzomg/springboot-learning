package com.zzw.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

//    @DeleteMapping
//    @PutMapping
//    @GetMapping
//    @PostMapping

    //@RequestMapping(value = "/user/login",method = RequestMethod.POST)
    //表示要映射一个post请求
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session) {
        // StringUtils.isEmpty()判断一个字符串是否为空（null）和（""）
        if (!StringUtils.isEmpty(username) && "123456".equals(password)) {
            //登陆成功，转发页面，url不变，为防止表单重复提交，可以重定向到主页
            //把登录状态放在session中
            session.setAttribute("loginUser", username);
            return "redirect:/main.html";
        } else {
            //登陆失败
            map.put("msg", "用户名或密码错误");
            return "login";
        }
    }
}
