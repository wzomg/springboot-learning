package com.zzw.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;


/**
 * 此项目使用的是嵌入式tomcat
 * SpringBoot对静态资源的映射规则
 *     1）、所有/webjars/**，都去classpath:/META-INF/resources/webjars/路径下找资源
 *     webjars：以jar包的方式引入静态资源；
 *     2）、/**：表示访问当前项目的任何资源，先去controller层中找是否有对应的处理请求，否则默认去以下4个路径中查找静态资源文件：
 *          "classpath:/META-INF/resources/",
 *          "classpath:/resources/",
 *          "classpath:/static/",
 *          "classpath:/public/"
 *     3）、配置欢迎页映射：静态资源文件夹下的所有index.html页面，被/**映射，例如：访问路径为：localhost:8080/   则默认去找index页面
 *     4）、所有的 /**favicon.ico都是在静态资源文件夹下去查找
 *
 * 模板引擎：JSP、Velocity、Freemarker、Thymeleaf
 * springBoot推荐的模板引擎为Thymeleaf
 * 只要把html页面放在classpath:/templates/下，thymeleaf就能自动渲染；
 *
 */


@SpringBootApplication
public class SpringBoot02WebRestfulcrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot02WebRestfulcrudApplication.class, args);
    }

    /**
     * 给容器中添加一个视图解析器，自动将其组合进来
     * @return
     */
    /*public ViewResolver MyViewResolver() {
        return new MyViewResolver();
    }

    private static class MyViewResolver implements ViewResolver {

        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }*/
}