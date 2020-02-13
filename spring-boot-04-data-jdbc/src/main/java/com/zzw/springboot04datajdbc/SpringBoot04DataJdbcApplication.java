package com.zzw.springboot04datajdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBoot04DataJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot04DataJdbcApplication.class, args);
    }

}
/*

SpringBoot默认使用com.zaxxer.hikari.HikariDataSource作为数据源
数据源的相关配置都在 DataSourceProperties里面；
自动配置原理：
jdbc的相关配置都在 org.springframework.boot.autoconfigure.jdbc 包下
1、参考DataSourceConfiguration，根据配置创建数据源，默认使用 Hikari 连接池；可以使用 spring.datasource.type 指定自定义的数据源类型；
2、springboot默认支持的连池：
    org.apache.tomcat.jdbc.pool.DataSource
    com.zaxxer.hikari.HikariDataSource
    org.apache.commons.dbcp2.BasicDataSource

自定义数据源类型：
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(DataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type")
static class Generic {
    @Bean
    DataSource dataSource(DataSourceProperties properties) {
    //使用 DataSourceBuilder 创建数据源，利用反射创建相应 type 的数据源，并且绑定相关属性
        return properties.initializeDataSourceBuilder().build();
    }
}

SpringBoot在创建连接池后还会运行预定义的SQL脚本文件，具体参考配置类:
org.springframework.boot.autoconfigure.jdbc.DataSourceInitializationConfiguration
在该类中注册了 dataSourceInitializerPostProcessor

DataSourceInitializerInvoker实现了ApplicationListener接口
作用：
    1）、afterPropertiesSet() --> createSchema();建表语句
    2）、initSchema(); 运行插入建表数据的sql语句；
    3）、默认只需将文件命名为：schema-*.sql（建表）、data-*.sql（数据文件）
    fallback为 schema 或 data
    注意：项目每次启动都会执行一次sql

private List<Resource> getScripts(String propertyName, List<String> resources, String fallback) {
    if (resources != null) {
        return getResources(propertyName, resources, true);
    }
    String platform = this.properties.getPlatform();
    List<String> fallbackResources = new ArrayList<>();
    fallbackResources.add("classpath*:" + fallback + "-" + platform + ".sql");
    fallbackResources.add("classpath*:" + fallback + ".sql");
    return getResources(propertyName, fallbackResources, false);
}
    3）、可以看出，如果我们没有在配置文件中配置脚本的具体位置，就会在classpath下找schema-all.sql和schema.sql
    platform获取的是all，platform可以在配置文件中修改


建表语句：
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `departmentName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
*/
