package com.zzw.springboot06datajpa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity //使用JPA注解配置映射关系,告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "tb_user")  // @Table来指定和哪个数据表对应；如果省略默认表名为类名小写；
public class User {

    @Id //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键的生成策略：自增主键
    private Integer id;
    @Column(name = "last_name", length = 50) //这是和数据表对应的一个列
    private String lastName;
    @Column //省略默认列名就是属性名
    private String email;
}
