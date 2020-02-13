package com.zzw.springboot03webjsp;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //参数是@SpringBootApplication标注的主类
        return application.sources(SpringBoot03WebJspApplication.class);
    }
}
