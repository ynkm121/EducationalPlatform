package com.liu.service;

import com.liu.entity.Department;
import com.liu.util.PageQueryUtils;
import java.util.List;

public interface DepartmentService {

    Department getDepNameByID(Integer deprtmentId);

    List<Department> getAllDepartment(PageQueryUtils utils);

    List<String> getDepartmentName();

    Department getDepIdByName(String departmentName);
}
