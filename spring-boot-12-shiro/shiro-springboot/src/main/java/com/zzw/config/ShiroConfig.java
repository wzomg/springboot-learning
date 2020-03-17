package com.zzw.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

// Shiro 配置类
@Configuration
public class ShiroConfig {

    // 创建 ShiroFilterFactoryBean：第3步，使用 @Qualifier 来找到对应的安全管理器
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 添加 shiro 的内置过滤器
        /*
        * anon：无需认证就可以访问
        * authc：必须认证才能访问
        * user：必须拥有 “记住我” 功能才能用
        * perms：拥有对某个资源的权限才能访问
        * role：拥有某个角色权限才能访问
        * */
        // 使用LinkedHashMap来保证添加的顺序
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();

        // 注意和需要认证的添加顺序，不需要拦截的放在前面
        // 某些请求需要进行授权操作，正常情况下，没有授权会跳转到未授权页面
        filterMap.put("/user/add", "perms[user:add]");
        filterMap.put("/user/update", "perms[user:update]");


        // 请求url：/user/* 必须经过认证
        filterMap.put("/user/*", "authc");

        // 设置过滤器
        bean.setFilterChainDefinitionMap(filterMap);

        // 设置登录请求
        bean.setLoginUrl("/toLogin");

        // 设置未授权url跳转到未授权提示页面
        bean.setUnauthorizedUrl("/noAuth");

        // 设置安全管理器
        bean.setSecurityManager(securityManager);
        return bean;
    }

    // 创建DefaultWebSecurityManager：第2步，使用 @Qualifier 来找到对应的 Realm
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联 自定义的 Realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    // 创建 Realm 对象，需要自定义类：第1步
    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        return new UserRealm();
    }


    // 配置 ShiroDialect，用于 thymeleaf 和 shiro 标签配合使用
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
