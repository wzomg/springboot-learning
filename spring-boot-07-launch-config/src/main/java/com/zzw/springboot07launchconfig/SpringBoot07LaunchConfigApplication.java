package com.zzw.springboot07launchconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBoot07LaunchConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot07LaunchConfigApplication.class, args);
    }
}

/*
自定义starter
启动器只用来做依赖导入
专门来写一个自动配置模块；
启动器依赖自动配置模块，项目中引入相应的starter就会引入启动器的所有传递依赖
启动器模块是一个空 JAR 文件，仅提供辅助性依赖管理，这些依赖可能用于自动装配或者其他类库
官方命名：spring-boot-starter-模块名
eg：spring-boot-starter-web、spring-boot-starter-jdbc、spring-boot-starter-thymeleaf
自定义命名：
模块名-spring-boot-starter
eg：mybatis-spring-boot-start

@Configuration //指定这个类是一个配置类
@ConditionalOnXXX //在指定条件成立的情况下自动配置类生效
@AutoConfigureAfter //指定自动配置类的顺序
@Bean //给容器中添加组件
@ConfigurationProperties 结合相关xxxProperties类来绑定相关的配置
@EnableConfigurationProperties //让xxxProperties生效加入到容器中
public class XxxxAutoConfiguration {

自动配置类要能加载,将需要启动就加载的自动配置类配置在META-INF/spring.factories中
eg：
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.mybatis.spring.boot.autoconfigure.MybatisLanguageDriverAutoConfiguration,\
org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration,\

 */