package com.zzw.springboot05datamybatis.controller;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import com.zzw.springboot05datamybatis.mapper.DepartmentMapper;
import com.zzw.springboot05datamybatis.mapper.EmployeeMapper;
import com.zzw.springboot05datamybatis.pojo.Department;
import com.zzw.springboot05datamybatis.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    // url：http://localhost:8080/dept/1
    // 使用占位符的方式
    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable("id") Integer id){
        return departmentMapper.getDeptById(id);
    }
    // url：http://localhost:8080/dept?departmentName=PeppaPig
    @GetMapping("/dept")
    public Department insertDept(Department department){
        departmentMapper.insertDept(department);
        return department;
    }
    // url：http://localhost:8080/emp/1
    @GetMapping("/emp/{id}")
    public Employee getEmp(@PathVariable("id") Integer id){
        return employeeMapper.getEmpById(id);
    }
}
