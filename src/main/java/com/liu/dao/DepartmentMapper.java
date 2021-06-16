package com.liu.dao;

import com.liu.entity.Department;
import com.liu.util.PageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer departmentId);

    int insert(Department record);

    Department selectByPrimaryKey(Integer departmentId);

    List<Department> selectAll(PageQueryUtils utils);

    int updateByPrimaryKey(Department record);

    Department selectByDepartName(String departmentName);
}