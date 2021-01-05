package com.zzw.springboot.config;


import com.zzw.springboot.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 实现WebMvcConfigurer接口用来扩展SpringMVC的功能，注意要在这个类上加上注解配置
 * 编写一个配置类MyMvcConfig（@Configuration），其实现接口WebMvcConfigurer，不能标注@EnableWebMvc
 * 特点：既保留了所有的自动配置，也能用我们扩展的配置；
 * 原理：
 *      1）、WebMvcAutoConfiguration是SpringMVC的自动配置类；
 *      2）、在做其他自动配置时会导入 @Import({WebMvcAutoConfiguration.EnableWebMvcConfiguration.class})
 *      3）、容器中所有的WebMvcConfigurer都会一起起作用，然后自个定义的配置类也会被调用
 *      4）、效果：SpringMVC的自动配置和我们扩展配置都会一起起作用
 *
 * 全面接管SpringMVC：也就是不需要SpringBoot对SpringMVC的自动配置，所有都是我们自己来配置，所有的SpringMvc自动配置都会失效：
 *      使用方法：在配置类上添加注解@EnableWebMvc
 *      原理：@EnableWebMvc将WebMvcConfigurationSupport组件导进来，覆盖了之前的方法，变成全都要自己编写和配置
 */

//@EnableWebMvc
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {


    //定义不拦截路径,/user/login为登录验证请求
    private static final String[] excludePaths = {"/", "/index", "/index.html", "/asserts/**", "/user/login", "/webjars/**"};

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //单单实现页面跳转
        //效果：浏览器发送 http://localhost:8080/zzw 请求跳转到success页面
        registry.addViewController("/zzw").setViewName("success");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加不拦截的路径
        /*registry.addInterceptor(new LoginHandlerInterceptor())
                .excludePathPatterns(excludePaths);*/
    }

    // 将区域信息解析器加载到spring容器中
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

}
