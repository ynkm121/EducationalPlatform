package com.liu.dao;

import com.liu.entity.CourseRelation;
import com.liu.util.PageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseRelationMapper {

    List<CourseRelation> selectByCourseId(Integer courseId);

    // used
    List<CourseRelation> selectByStudentId(PageQueryUtils utils);

    //used
    CourseRelation selectByStudentIdAndCourseId(Long studentId, Integer courseId);

    List<CourseRelation> selectAll();

    //used
    int BatchInsert(List<CourseRelation> relations);


    List<CourseRelation> selectForScalePages(PageQueryUtils utils);

    int selectCountSelective(PageQueryUtils utils);

    int updateScale(Long studentId, Integer courseId, @Param("studentScore")Float finalNum);
}