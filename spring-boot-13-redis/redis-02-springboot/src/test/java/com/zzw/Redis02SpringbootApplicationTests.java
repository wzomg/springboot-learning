package com.zzw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zzw.pojo.User;
import com.zzw.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class Redis02SpringbootApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
        // opsForValue() 操作字符串
        // opsForList() 操作list
        // opsForSet() 操作set
        // opsForHash() 操作hash
        // opsForZSet() 操作zset
        // opsForGeo() 操作Geo
        // opsForHyperLogLog() 操作基数
        redisTemplate.opsForValue().set("mykey", "zhangsan");
        System.out.println(redisTemplate.opsForValue().get("mykey"));
    }

    @Test
    public void test1() throws JsonProcessingException {
        User user = new User("张三", 3);
        // String jsonUser = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user", user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

    @Test
    public void test2() {
        redisUtil.set("name", "zhangsan");
        System.out.println(redisUtil.get("name"));
    }
}
