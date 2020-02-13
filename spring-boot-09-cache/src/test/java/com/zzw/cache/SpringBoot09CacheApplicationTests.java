package com.zzw.cache;

import com.zzw.cache.mapper.EmployeeMapper;
import com.zzw.cache.pojo.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;


@SpringBootTest
class SpringBoot09CacheApplicationTests {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate; // 操作k-v都是字符串的

    @Autowired
    private RedisTemplate redisTemplate; // k-v都是对象的

    @Autowired
    RedisTemplate<Object, Employee> empRedisTemplate;

    /**
     * Redis 常见的五大数据类型
     * String、List、Set、Hash、Zset
     */
    @Test
    public void test01() {
        // stringRedisTemplate.opsForValue().append("msg", "hello");
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);
    }

    @Test
    public void test02() {
        Employee empById = employeeMapper.getEmpById(1);
        // 如果保存对象，jdk默认使用序列化机制，序列化后的数据保存到redis中
        // redisTemplate.opsForValue().set("emp-01", empById);
        // 可以自定义改变默认的序列化规则
        empRedisTemplate.opsForValue().set("emp-01", empById);
    }


    @Test
    void contextLoads() {
        Employee empById = employeeMapper.getEmpById(1);
        System.out.println(empById);
    }

}
