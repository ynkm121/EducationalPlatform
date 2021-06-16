package com.liu.service.Impl;

import com.liu.controller.admin.vo.StudentVO;
import com.liu.dao.StudentMapper;
import com.liu.entity.Department;
import com.liu.entity.Student;
import com.liu.service.DepartmentService;
import com.liu.service.StudentService;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    DepartmentService departmentService;

    @Override
    public Student getStudentById(Long studentId) {
        return studentMapper.getStudentById(studentId);
    }

    @Override
    public PageResult getStudentPages(PageQueryUtils utils) {
        List<Student> students = studentMapper.getStudentPages(utils);
        List<StudentVO> studentVOS = getVOByStudent(students);
        utils.remove("page");
        int count = studentMapper.getCounts(utils);
        return new PageResult(count, utils.getLimit(), utils.getPage(), studentVOS);
    }

    @Override
    public boolean insertStudent(Student student) {
        return studentMapper.insertSelective(student) > 0;
    }

    @Override
    public boolean updateStudent(Student student) {
        return studentMapper.updateByPrimaryKeySelective(student) > 0;
    }

    @Override
    public boolean BatchDelete(Long[] ids) {
        return studentMapper.BatchDelete(ids) > 0;
    }

    @Override
    public boolean BatchConfirm(Long[] ids) {
        return studentMapper.BatchConfirm(ids) > 0;
    }

    @Override
    public StudentVO getStudentVO(Long studentId) {
        Student student = studentMapper.getStudentById(studentId);
        StudentVO vo = new StudentVO();
        BeanUtils.copyProperties(student, vo);
        Department department = departmentService.getDepNameByID(student.getDepartment());
        vo.setDepartmentName(department.getDepartmentName());
        return vo;
    }

    @Override
    public int getAllCount() {
        return studentMapper.getCounts(null);
    }

    private List<StudentVO> getVOByStudent(List<Student> students) {
        LinkedList<StudentVO> studentVOS = new LinkedList<>();
        if(students != null){
            List<Department> departments = departmentService.getAllDepartment(null);
            Map<Integer, String> map = departments.stream().collect(Collectors.toMap(Department::getDepartmentId, Department::getDepartmentName));
            for (Student student : students) {
                StudentVO vo = new StudentVO();
                BeanUtils.copyProperties(student, vo);
                String depName = map.get(student.getDepartment());
                vo.setDepartmentName(depName);
                studentVOS.add(vo);
            }
        }
        return studentVOS;
    }
}
