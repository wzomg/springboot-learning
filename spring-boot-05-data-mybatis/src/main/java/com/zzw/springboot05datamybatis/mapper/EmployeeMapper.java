package com.zzw.springboot05datamybatis.mapper;


import com.zzw.springboot05datamybatis.pojo.Employee;

//@Mapper或者@MapperScan将接口扫描装配到容器中
//@Mapper
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public void insertEmp(Employee employee);
}
