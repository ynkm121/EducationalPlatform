package com.liu.service;

import com.liu.controller.student.vo.CourseRelationVO;
import com.liu.entity.CourseRelation;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;

import java.util.List;
import java.util.Map;

public interface CourseRelationService {

    PageResult getCourseRelationPage(PageQueryUtils utils);

    boolean insertCourseByIds(Integer[] ids, Long studentId);

    CourseRelation checkIfExistByIds(Long studentId, Integer courseId);

    List<CourseRelation> getRelationByStudentId(Long studentId);

    boolean checkPrereq(Long studentId, Integer courseId);

    PageResult getScalePages(PageQueryUtils utils);

    boolean updateScale(Long studentId, Integer courseId, Float finalNum);

}
