package com.zzw.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
// 开启 swagger2 功能
@EnableSwagger2
public class SwaggerConfig {
    // 访问地址：http://localhost:8080/swagger-ui.html

    // 配置多个 api 分组，多个Docket实例，注意 bean 不要重名
    @Bean
    public Docket docket1() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }

    @Bean
    public Docket docket2() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("B");
    }

    @Bean
    public Docket docket3() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("C");
    }


    // 配置了 swagger docket 的 bean 实例
    @Bean
    public Docket docket(Environment environment) {

        // 设置要显示的 swagger 环境
        Profiles profiles = Profiles.of("dev", "test");
        // 需求是在开发环境中启用 swagger ，在生产环境中 不启用 swagger
        // 通过 environment.acceptsProfiles 判断是否处在自己设定的环境当中
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // enable() 表示是否启用 swagger
                .enable(flag)
                // 配置 api 文档的分组
                .groupName("zzw")
                .select()
                // RequestHandlerSelectors 配置要扫描接口的方式
                // .apis(RequestHandlerSelectors.basePackage("com.zzw.swagger.controller"))
                // any()：扫描全部
                // .apis(RequestHandlerSelectors.any())
                // none()：都不扫描
                // .apis(RequestHandlerSelectors.none())
                // withClassAnnotation：扫描类上的注解，参数是一个注解的反射对象
                // .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                // withMethodAnnotation：扫描方法上的注解
                // .apis(RequestHandlerSelectors.withMethodAnnotation(GetMapping.class))
                // basePackage：指定要扫描的的包
                .apis(RequestHandlerSelectors.basePackage("com.zzw.swagger.controller"))
                // paths()：过滤什么路径，ant()表示只扫描例如：/zzw带前缀的所有路由
                // .paths(PathSelectors.ant("/zzw/**"))
                .build();
    }

    // 配置 swagger 信息： apiInfo
    private ApiInfo apiInfo() {
        // 作者信息
        Contact contact = new Contact("zzw", "http://localhost:8080", "233@163.com");
        return new ApiInfo("个人博客的 swagger api文档",
                "Api在线查阅",
                "v1.0",
                "http://localhost:8080",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
