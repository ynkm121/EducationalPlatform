package com.liu.service.Impl;

import com.liu.dao.TeacherMapper;
import com.liu.entity.Teacher;
import com.liu.service.TeacherService;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public Teacher getTeacherById(Integer teacherId) {
        return teacherMapper.selectByPrimaryKey(teacherId);
    }

    @Override
    public List<String> getAllTeacherName() {
        List<Teacher> teachers = teacherMapper.selectAll(null);
        List<String> teacherNames = teachers.stream().map(Teacher::getTeacherName).collect(Collectors.toList());
        return teacherNames;
    }

    @Override
    public Teacher getTeacherByName(String teacherName) {
        return teacherMapper.selectByTeacherName(teacherName);
    }

    @Override
    public PageResult getTeacherPages(PageQueryUtils utils) {
        List<Teacher> teachers = teacherMapper.selectAll(utils);
        utils.remove("page");
        int count = teacherMapper.getCount(utils);
        return new PageResult(count, utils.getLimit(), utils.getPage(), teachers);
    }

    @Override
    public boolean insertTeacher(Teacher teacher) {
        return teacherMapper.insertSelective(teacher) > 0;
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        return teacherMapper.updateByPrimaryKey(teacher) > 0;
    }

    @Override
    public boolean BatchDelete(Integer[] ids) {
        return teacherMapper.BatchDelete(ids) > 0;
    }

    @Override
    public boolean BatchConfirm(Integer[] ids) {
        return teacherMapper.BatchConfirm(ids) > 0;
    }

    @Override
    public int getAllCount() {
        return teacherMapper.getCount(null);
    }
}
