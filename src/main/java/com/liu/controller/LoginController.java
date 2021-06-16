package com.liu.controller;

import com.liu.controller.security.CustomToken;
import com.liu.entity.Admin;
import com.liu.entity.Student;
import com.liu.entity.Teacher;
import com.liu.service.AdminService;
import com.liu.service.DepartmentService;
import com.liu.service.StudentService;
import com.liu.service.TeacherService;
import com.liu.util.Result;
import com.liu.util.ResultGenerator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    AdminService adminService;
    @Autowired
    DepartmentService departmentService;

    @GetMapping({"/","/login", "/index", "/index.html"})
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        HttpSession session){
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            session.setAttribute("errMsg", "用户名或密码错误");
        }
        if(username.length() != 5 && username.length() != 6 && username.length() != 11){
            session.setAttribute("errMsg", "用户名或密码错误");
        }
        Long userId;
        try{
            userId = Long.parseLong(username);
        }catch (NumberFormatException e){
            session.setAttribute("errMsg", "非法用户名");
            return "redirect:login";
        }
        String LoginType;
        switch (username.length()){
            case 5:LoginType = "Admin";break;
            case 6:LoginType = "Teacher";break;
            default: LoginType = "Student";break;
        }
        Subject subject = SecurityUtils.getSubject();
        CustomToken token = new CustomToken(username, password, LoginType);
        try {
            subject.login(token);
        }catch (UnknownAccountException e){
            session.setAttribute("errMsg", e.getMessage());
            return "redirect:login";
        }catch (IncorrectCredentialsException e){
            session.setAttribute("errMsg", "用户名或密码错误");
            return "redirect:login";
        }catch (AuthenticationException e){
            session.setAttribute("errMsg", "未知错误");
            return "redirect:login";
        }
        if(LoginType.equals("Student")){
            Student student = studentService.getStudentById(userId);
            session.setAttribute("studentId", student.getStudentId());
            session.setAttribute("studentName", student.getStudentName());
            session.setAttribute("path", "index");

            return "redirect:student";
        }
        if(LoginType.equals("Teacher")){
            Teacher teacher = teacherService.getTeacherById(userId.intValue());
            session.setAttribute("teacherId", teacher.getTeacherId());
            session.setAttribute("teacherName", teacher.getTeacherName());
            session.setAttribute("path", "index");

            return "redirect:teacher";
        }
        Admin admin = adminService.getAdminById(userId.intValue());
        session.setAttribute("adminId", admin.getAdminId());
        session.setAttribute("adminName", admin.getAdminName());
        session.setAttribute("path", "index");

        return "redirect:admin";
    }

    @RequestMapping("/unauth")
    public String unauth(HttpSession session){
        session.setAttribute("errMsg", "未授权界面");
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register/student")
    @ResponseBody
    public Result student(@RequestParam("studentName")String studentName,
                          @RequestParam("studentId")Long studentId,
                          @RequestParam("studentGrade")Integer studentGrade,
                          @RequestParam("studentPwd")String password,
                          @RequestParam("age")Integer age,
                          @RequestParam("sex")Byte sex,
                          @RequestParam("studentDepart")Integer department){
        if(StringUtils.isEmpty(studentName) || StringUtils.isEmpty(password) || studentId == null || studentGrade == null || age == null || sex == null || department == null){
            return ResultGenerator.genFailResult("参数异常");
        }
        if(studentName.length() < 2 || studentName.length() > 18){
            return ResultGenerator.genFailResult("用户名长度不符");
        }
        if(studentId.toString().length() != 11){
            return ResultGenerator.genFailResult("id长度不符");
        }
        Student student = new Student();
        student.setStudentId(studentId);
        student.setStudentName(studentName);
        student.setStudentGrade(studentGrade);
        student.setPassword(password);
        student.setAge(age);
        student.setSex(sex);
        student.setDepartment(department);
        student.setIsRegist((byte)0);
        if(studentService.insertStudent(student)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作失败");
        }
    }

    @PostMapping("/register/teacher")
    @ResponseBody
    public Result teacher(@RequestParam("teacherName")String teacherName,
                          @RequestParam("teacherId")Integer teacherId,
                          @RequestParam("teacherPwd")String password){
        if(StringUtils.isEmpty(teacherName) || StringUtils.isEmpty(password) || teacherId == null){
            return ResultGenerator.genFailResult("参数异常");
        }
        if(teacherName.length() < 2 || teacherName.length() > 18){
            return ResultGenerator.genFailResult("用户名长度不符");
        }
        if(teacherId.toString().length() != 6){
            return ResultGenerator.genFailResult("id长度不符");
        }
        Teacher teacher = new Teacher();
        teacher.setTeacherId(teacherId);
        teacher.setTeacherName(teacherName);
        teacher.setIsRegist((byte)0);
        teacher.setPassword(password);
        if(teacherService.insertTeacher(teacher)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作失败");
        }
    }

}
