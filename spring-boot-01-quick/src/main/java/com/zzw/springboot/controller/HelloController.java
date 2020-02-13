package com.zzw.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ResponseBody： 这个类中所有方法返回的数据直接写给浏览器，若是对象则转为json数据
 * @Controller
 */

/*@ResponseBody
@Controller*/
@RestController
public class HelloController {

    //url访问地址：http://localhost:8090/hello
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World quick!";
    }
}
