package com.zzw.springboot05datamybatis.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

// 使用的是全类名注解
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {

    // 开启mybatis的驼峰命名配置，也可以使用配置文件的方式开启
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {

            @Override
            public void customize(Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
