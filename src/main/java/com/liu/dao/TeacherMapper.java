package com.liu.dao;

import com.liu.entity.Teacher;
import com.liu.util.PageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherMapper {

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer teacherId);

    List<Teacher> selectAll(PageQueryUtils utils);

    int updateByPrimaryKey(Teacher record);

    Teacher selectByTeacherName(String teacherName);

    int getCount(PageQueryUtils utils);

    int BatchDelete(Integer[] ids);

    int BatchConfirm(Integer[] ids);
}