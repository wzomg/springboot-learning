package com.zzw.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


// 导入Spring的配置文件让其生效，参数为一个数组，@ImportResource标注在一个配置类上，来加载Spring的配置文件
// 其可被配置类myAppConfig所取代（Springboot推荐方式：使用配置类（全注解方式）代替Spring配置文件）
//@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class SpringBoot01QuickApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot01QuickApplication.class, args);
    }
}

