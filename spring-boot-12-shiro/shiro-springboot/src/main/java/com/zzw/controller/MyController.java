package com.zzw.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping({"/","/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg", "Hello,Shiro!");
        return "index";
    }

    @GetMapping("/user/add")
    public  String add() {
        return "user/add";
    }
    @GetMapping("/user/update")
    public  String update() {
        return "user/update";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return  "login";
    }

    @GetMapping("/noAuth")
    public String noAuth() {
        return "noAuth";
    }

    @GetMapping("/login")
    public String login(String username, String password, Model model) {
        // 1、获取 Subject
        Subject subject = SecurityUtils.getSubject();

        // 2、使用令牌封装登录的用户信息
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        // 3、执行登录方法
        try {
            subject.login(token);
            // 若没有任何登录异常，则表示登录成功，然后应该重定向到主页面
            // 跳转到 login.html
            return "redirect:/index";
        } catch (UnknownAccountException e) {
            // 登录失败：用户名不存在
            model.addAttribute("msg", "用户名不存在");
            // 这里不能使用重定向，否则跳转页面时无法携带错误消息
            return "login";
        } catch (IncorrectCredentialsException e) {
            // 登录失败：密码错误
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }
}
