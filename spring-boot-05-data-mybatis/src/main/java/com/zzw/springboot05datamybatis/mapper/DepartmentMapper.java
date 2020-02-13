package com.zzw.springboot05datamybatis.mapper;

import com.zzw.springboot05datamybatis.pojo.Department;
import org.apache.ibatis.annotations.*;


//@Mapper //指定这是接口是一个操作数据库的mapper
public interface DepartmentMapper {

    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);

    @Delete("delete from department where id=#{id}")
    public int deleteDeptById(Integer id);

    //获取自增的id
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into department(department_name) values(#{departmentName})")
    public int insertDept(Department department);

    @Update("update department set department_name=#{departmentName} where id=#{id}")
    public int updateDept(Department department);
}
