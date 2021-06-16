package com.liu.controller.admin;

import com.liu.entity.Department;
import com.liu.entity.Student;
import com.liu.service.DepartmentService;
import com.liu.service.StudentService;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import com.liu.util.Result;
import com.liu.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.awt.image.IntegerInterleavedRaster;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminStudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/student")
    public String index(Model model){
        model.addAttribute("path", "student");
        return "admin/student";
    }

    @PostMapping("/student/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params){
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page"))){
            return ResultGenerator.genFailResult("参数异常!");
        }
        params.put("isRegist", 1);
        PageQueryUtils utils = new PageQueryUtils(params);
        PageResult pageResult = studentService.getStudentPages(utils);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    @PostMapping("/student/regist/list")
    @ResponseBody
    public Result unregist(@RequestParam Map<String, Object> params){
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page"))){
            return ResultGenerator.genFailResult("参数异常!");
        }
        params.put("isRegist", 0);
        PageQueryUtils utils = new PageQueryUtils(params);
        PageResult pageResult = studentService.getStudentPages(utils);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    @GetMapping("/student/departInfo")
    @ResponseBody
    public Result departInfo(){
        List<String> names = departmentService.getDepartmentName();
        if(names != null){
            return ResultGenerator.genSuccessResult(names);
        }else{
            return ResultGenerator.genFailResult("操作错误");
        }
    }

    @PostMapping("/student/save")
    @ResponseBody
    public Result save(@RequestParam("studentName")String studentName,
                       @RequestParam("departmentName")String departmentName,
                       @RequestParam("studentGrade") Integer studentGrade,
                       @RequestParam("password")String password,
                       @RequestParam("age")Integer age,
                       @RequestParam("sex")Byte sex){
        if(StringUtils.isEmpty(studentName) || StringUtils.isEmpty(departmentName) || studentGrade == null || StringUtils.isEmpty(password) || age == null || sex == null){
            return ResultGenerator.genFailResult("参数异常");
        }
        Department department = departmentService.getDepIdByName(departmentName);
        Student student = new Student();
        student.setStudentName(studentName);
        student.setDepartment(department.getDepartmentId());
        student.setStudentGrade(studentGrade);
        student.setPassword(password);
        student.setAge(age);
        student.setSex(sex);
        student.setIsRegist((byte)1);
        if(studentService.insertStudent(student)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作错误");
        }
    }

    @PostMapping("/student/update")
    @ResponseBody
    public Result update(@RequestParam("studentId")Long studentId,
                       @RequestParam("studentName")String studentName,
                       @RequestParam("departmentName")String departmentName,
                       @RequestParam("studentGrade") Integer studentGrade,
                       @RequestParam("password")String password,
                       @RequestParam("age")Integer age,
                       @RequestParam("sex")Integer sex){
        if(StringUtils.isEmpty(studentName) || StringUtils.isEmpty(departmentName) || studentGrade == null || StringUtils.isEmpty(password) || age == null || sex == null){
            return ResultGenerator.genFailResult("参数异常");
        }
        Department department = departmentService.getDepIdByName(departmentName);
        Student student = studentService.getStudentById(studentId);
        student.setStudentName(studentName);
        student.setDepartment(department.getDepartmentId());
        student.setStudentGrade(studentGrade);
        student.setPassword(password);
        student.setAge(age);
        student.setSex((byte)sex.intValue());
        if(studentService.updateStudent(student)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作错误");
        }
    }

    @PostMapping("/student/delete")
    @ResponseBody
    public Result delete(@RequestBody Long[] ids){
        if(ids.length < 1){
            return ResultGenerator.genFailResult("参数异常");
        }
        if(studentService.BatchDelete(ids)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作失败");
        }
    }

    @PostMapping("/student/confirm")
    @ResponseBody
    public Result confirm(@RequestBody Long[] ids){
        if(ids.length < 1){
            return ResultGenerator.genFailResult("参数异常");
        }
        if(studentService.BatchConfirm(ids)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作失败");
        }
    }
}
