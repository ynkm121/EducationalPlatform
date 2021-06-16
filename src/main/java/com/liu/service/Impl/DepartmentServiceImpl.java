package com.liu.service.Impl;

import com.liu.dao.DepartmentMapper;
import com.liu.entity.Department;
import com.liu.service.DepartmentService;
import com.liu.util.PageQueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Department getDepNameByID(Integer deprtmentId) {
        return departmentMapper.selectByPrimaryKey(deprtmentId);
    }

    @Override
    public List<Department> getAllDepartment(PageQueryUtils utils) {
        return departmentMapper.selectAll(utils);
    }

    @Override
    public List<String> getDepartmentName() {
        List<Department> departments = departmentMapper.selectAll(null);
        return departments.stream().map(Department::getDepartmentName).collect(Collectors.toList());
    }

    @Override
    public Department getDepIdByName(String departmentName) {
        return departmentMapper.selectByDepartName(departmentName);
    }
}
