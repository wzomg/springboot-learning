package com.zzw.springboot.exception;

//继承运行时异常
public class UserNotExistException extends RuntimeException {

    public UserNotExistException() {
        super("用户不存在");
    }
}
