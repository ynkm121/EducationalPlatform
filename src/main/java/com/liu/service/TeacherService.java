package com.liu.service;

import com.liu.entity.Teacher;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;

import java.util.List;

public interface TeacherService {

    Teacher getTeacherById(Integer teacherId);

    List<String> getAllTeacherName();

    Teacher getTeacherByName(String teacherName);

    PageResult getTeacherPages(PageQueryUtils utils);

    boolean insertTeacher(Teacher teacher);

    boolean updateTeacher(Teacher teacher);

    boolean BatchDelete(Integer[] ids);

    boolean BatchConfirm(Integer[] ids);

    int getAllCount();
}
