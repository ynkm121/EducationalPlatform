package com.liu.service;

import com.liu.entity.Course;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;

import java.util.List;

public interface CourseService {

    Course getByCourseId(Integer courseId);

    PageResult getByPageUtils(PageQueryUtils utils);

    PageResult getByPageUtilsForAdmin(PageQueryUtils utils);

    boolean insertCourse(Course course);

    boolean BatchDelete(Integer[] ids);

    boolean updateCourse(Course course);

    List<Course> getByTeacherId(Integer teacherId);

    int getAllCount();

    List<Course> getAll();

}
