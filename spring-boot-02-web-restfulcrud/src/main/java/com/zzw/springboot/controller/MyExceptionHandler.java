package com.zzw.springboot.controller;

import com.zzw.springboot.exception.UserNotExistException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

// SpringMvc异常处理器
@ControllerAdvice
public class MyExceptionHandler  {

    //1、浏览器客户端返回的都是json
    //专门处理UserNotExistException异常
    /*@ResponseBody
    @ExceptionHandler(UserNotExistException.class)
    public Map<String,Object> handleException(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("code","user.notexist");
        map.put("message",e.getMessage());
        return map;
    }*/
    @ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        /**
         * Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
         */
        //给请求域传入我们自己的错误状态码  4xx 5xx
        request.setAttribute("javax.servlet.error.status_code", 500);
        map.put("code", "user.notexist");
        map.put("message", "用户出错啦");
        //将自定义的数据携带出去
        request.setAttribute("ext", map);
        //请求转发到/error，给BasicErrorController类处理
        //响应出去可以获取的数据是由getErrorAttributes（抽象类：AbstractErrorController实现（ErrorController））得到的
        //页面上能用的数据，或者是json返回能用的数据都是通过errorAttributes.getErrorAttributes处理返回的
        //容器中DefaultErrorAttributes.getErrorAttributes()默认处理数据并返回
        return "forward:/error";
    }
}
