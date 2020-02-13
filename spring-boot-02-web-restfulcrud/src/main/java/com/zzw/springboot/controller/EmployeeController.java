package com.zzw.springboot.controller;

import com.zzw.springboot.dao.DepartmentDao;
import com.zzw.springboot.dao.EmployeeDao;
import com.zzw.springboot.entities.Department;
import com.zzw.springboot.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    //查询所有员工返回列表页面
    @GetMapping("/emps")
    public String list(Model model) {
        Collection<Employee> employees = employeeDao.getAll();
        model.addAttribute("emps", employees);
        return "emp/list";
    }

    //跳转到添加员工页面
    @GetMapping("/emp")
    public String toAddPage(Model model) {
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    //添加员工
    //SpringMVC自动将请求参数和入参对象的属性进行一一绑定；要求了解参数的名字和javaBean入参对象的属性名是一致的
    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        System.out.println("保存的员工信息为：" + employee);
        employeeDao.save(employee);
        //添加员工之后跳转到员工列表页面
        //redirect：表示重定向到一个地址（url会变）
        //forward：表示转发到一个地址（url不会变）
        return "redirect:/emps";
    }

    //跳到修改页面，查出当前员工，在页面回显数据，a标签get请求
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp", employee);

        //页面要显示所有的部门列表
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        //回到修改页面(add是一个修改添加二合一的页面);
        return "emp/add";
    }

    //修改员工信息；需要提交员工id；
    @PutMapping("/emp")
    public String updateEmployee(Employee employee) {
        System.out.println("修改的员工数据：" + employee);
        employeeDao.save(employee);
        // 重定向到员工列表页面
        return "redirect:/emps";
    }

    //员工删除
    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
