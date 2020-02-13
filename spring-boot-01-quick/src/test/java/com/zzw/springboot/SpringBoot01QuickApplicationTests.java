package com.zzw.springboot;

import com.zzw.springboot.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringBoot单元测试
 * 可以在测试期间很方便地类似编码进行自动注入配置文件等容器功能
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot01QuickApplicationTests {

    // 用法参考官方文档：https://www.slf4j.org
    // 记录器工厂，参数为获取记录信息的类名
    Logger logger = LoggerFactory.getLogger(getClass());

    //注入person
    @Autowired
    Person person;

    //注入ioc容器
    @Autowired
    ApplicationContext ioc;

    // 测试容器中是否有HelloService
    @Test
    public void testHelloService() {
        // 操作步骤：编写beans.xml文件，配置一个service bean的id，然后使用@ImportResource注解标注在主配置类上，值为指定的beans.xml配置文件
        boolean b = ioc.containsBean("helloService");
        System.out.println(b);
    }

    @Test
    public void contextLoads() {
        System.out.println(person);
        // 日志门面：SLF4J
        // 日志实现：Logback
        // SpringBoot 底层是Spring框架，Spring框架默认是用JCL；
        // SpringBoot 选用SLF4j和logback
        // 日志级别：
        // 由低到高：trace<debug<info<warn<error
        // 可以调整输出的日志级别：日志就只会在这个级别以后的高级别生效
        logger.trace("这是trace日志...");
        logger.debug("这是debug日志...");
        //SpringBoot默认给我们使用的是info级别，（在配置文件中）没有指定级别（logging.level.com.zzw=trace）就用SpringBoot默认规定的级别：root级别
        logger.info("这是info日志...");
        logger.warn("这是warn日志...");
        logger.error("这是error日志...");
    }
}
