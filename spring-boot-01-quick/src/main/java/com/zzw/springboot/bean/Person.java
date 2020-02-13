package com.zzw.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ConfigurationProperties(prefix = "person") 将本类中的所有属性与配置文件（application.yml）中相关值进行绑定
 *      prefix = "person"：配置文件（application.yml）中哪个下面的所有属性进行一一映射
 *      只有这个组件是容器中的组件（添加注解@Component），才能提供@ConfigurationProperties功能
 *
 * 以往Spring配置bean标签：
 * <bean class="Person">
 *      <property name="lastName" value="字面量、${key}从环境变量、配置文件中获取值、#{SpEL}（Spring表达式语法）"></property>
 * </bean>
 * 这个bean标签相当于在类成员属性前添加注解@Value(值)
 *
 * @ImportResource： 导入Spring的配置文件，让配置文件中的内容生效
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

//@PropertySource(value = {"classpath:person.properties"}) // 加载指定类路径下的配置文件：person.properties，避免全局文件中有太多的赋值操作，单个类作为单个文件比较好
@Component
@ConfigurationProperties(prefix = "person") // 默认从全局配置文件中获取值
//@Validated // 说明javabean：person需要进行（JSR303数据校验）
//@ConfigurationProperties支持JSR303数据校验和复杂数据类型封装，而@Value方式都不支持
public class Person {
    //${person.last-name}将类成员属性和properties文件中对应的属性绑定在一起
    //@Value("${person.last-name}") //此种写法不支持松散绑定，如：person.lastName与person.last-name不一样
    //@Email // 比如设定此注解，则lastName的值必须为电子邮箱的格式
    private String lastName;
    //@Value("#{11*2}") //此种语法支持SpEL表达式，但是在properties配置文件不支持这种写法
    private Integer age;
    //@Value("true")
    private Boolean boss;
    private Date birth;
    //@Value("${person.maps}") //此种方式将报错，@Value不支持复杂类型封装
    private Map<String, Object> maps;
    private List<Object> lists;

    private Dog dog;
}
