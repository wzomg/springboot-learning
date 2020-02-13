package com.zzw.cache;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 一、搭建基本环境
 * 1、导入数据库文件
 * 2、创建javaBean封装数据
 * 3、整合 mybatis 操作
 *      1）、配置数据源信息
 *      2)、使用注解版的mybatis
 *          1）、@MapperScan指定需要扫描的mapper接口所在的包
 * 二、快速体验缓存
 *     步骤：
 *          1、开启基于注解的缓存 @EnableCaching
 *          2、标注缓存注解即可
 *          @Cacheable
 *          @CachePut
 *          @CacheEvict
 * 三、测试缓存
 *      原理：CacheManager == Cache 缓存组件来实际给缓存中存取数据
 *          1）、引入 redis 的 starter，容器中保存的是 RedisCacheManager；
 *          2）、RedisCacheManager 帮我们创建 RedisCache 来作为缓存组件；RedisCache 通过操作redis来缓存数据
 *          3）、默认保存数据 k-v都是对象Object时，利用序列化来保存；如何保存为json？
 *              1、引入了redis的starter，CacheManager 变为 RedisCacheManager；
 *              2、默认创建的 RedisCacheManager 操作redis时 使用的是 RedisTemplate<K, V>
 *              3、RedisTemplate<K, V> 是默认使用jdk的序列化机制
 *          4）、自定义 RedisCacheManager 实现json序列化缓存数据
 *
 */
@MapperScan("com.zzw.cache.mapper")
@SpringBootApplication
@EnableCaching
public class SpringBoot09CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot09CacheApplication.class, args);
    }

}
