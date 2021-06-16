package com.liu.service;

import com.liu.controller.admin.vo.StudentVO;
import com.liu.entity.Student;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;

public interface StudentService {

    Student getStudentById(Long studentId);

    PageResult getStudentPages(PageQueryUtils utils);

    boolean insertStudent(Student student);

    boolean updateStudent(Student student);

    boolean BatchDelete(Long[] ids);

    boolean BatchConfirm(Long[] ids);

    StudentVO getStudentVO(Long studentId);

    int getAllCount();

}
