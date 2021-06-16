package com.liu.dao;

import com.liu.entity.Student;
import java.util.List;

import com.liu.util.PageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StudentMapper {


    int insertSelective(Student record);
      
    int updateByPrimaryKeySelective(Student record);

//    used
    Student getStudentById(Long studentId);

    List<Student> getStudentPages(PageQueryUtils utils);

    int getCounts(PageQueryUtils utils);

    int BatchDelete(Long[] ids);

    int BatchConfirm(Long[] ids);
}