package com.zzw;

import com.zzw.service.Impl.UserServiceImpl;
import com.zzw.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void contextLoads() {
        System.out.println(userService.findByName("zhangsan"));
    }
}
