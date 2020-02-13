package com.zzw.springboot05datamybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//
/*
使用@Mapper注解的类可以被扫描到容器中
@MapperScan相当于给所有接口添加Mapper注解当然可以，为了简单方便，只需标注在springboot启动类上
*/
@MapperScan(value = "com.zzw.springboot05datamybatis.mapper")
@SpringBootApplication
public class SpringBoot05DataMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot05DataMybatisApplication.class, args);
    }

}
