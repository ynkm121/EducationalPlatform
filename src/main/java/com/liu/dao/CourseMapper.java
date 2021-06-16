package com.liu.dao;

import com.liu.entity.Course;
import com.liu.util.PageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper {

    int insert(Course record);

    //used
    int updateSelective(Course course);

    List<Course> selectAll(PageQueryUtils utils);

    //used
    List<Course> selectUncheckedbyCourseId(List<Integer> courseId);


    int selectCount(PageQueryUtils utils);

    Course selectByPrimaryKey(Integer id);

    int BatchDelete(Integer[] ids);

    List<Course> selectByTeacherId(Integer teacherId);
}