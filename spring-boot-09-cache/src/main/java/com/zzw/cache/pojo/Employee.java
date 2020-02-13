package com.zzw.cache.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {  // 注意序列化要实现序列化接口
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;
    private Integer dId;
}
