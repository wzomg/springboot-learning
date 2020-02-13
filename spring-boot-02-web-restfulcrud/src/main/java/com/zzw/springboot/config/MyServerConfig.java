package com.zzw.springboot.config;

import com.zzw.springboot.filter.MyFilter;
import com.zzw.springboot.listener.MyListener;
import com.zzw.springboot.servlet.MyServlet;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;
import java.util.Arrays;


//由于SpringBoot默认是以jar包的方式启动嵌入式的Servlet容器来启动SpringBoot的web应用，没有web.xml文件。
//默认拦截：/ 所有请求；包括静态资源，但是不拦截jsp请求；/*会拦截jsp
//可以通过 server.servletPath 来修改SpringMVC前端控制器默认拦截的请求路径
@Configuration
public class MyServerConfig {

    // 注册三大组件
    // 向容器中添加ServletRegistrationBean
    @Bean
    public ServletRegistrationBean  myServlet() {
        ServletRegistrationBean register = new ServletRegistrationBean(new MyServlet(), "/myServlet");
        // 设置启动顺序
        register.setLoadOnStartup(1);
        return register;
    }

    //向容器中添加FilterRegistrationBean
    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean register = new FilterRegistrationBean(new MyFilter());
        register.setUrlPatterns(Arrays.asList("/myServlet","/"));
        return register;
    }


    //向容器中注入ServletListenerRegistrationBean
    @Bean
    public ServletListenerRegistrationBean myListener(){
        return new ServletListenerRegistrationBean<MyListener>(new MyListener());
    }

    //SpringBoot默认使用的是Tomcat
    //配置嵌入式的Servlet容器
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            //定制嵌入式的servlet容器相关规则，代码方式的配置会覆盖配置文件的配置
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                factory.setPort(8081);
            }
        };
    }

}
