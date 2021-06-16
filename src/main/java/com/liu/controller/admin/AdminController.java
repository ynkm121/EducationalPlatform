package com.liu.controller.admin;

import com.liu.service.CourseService;
import com.liu.service.StudentService;
import com.liu.service.TeacherService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(Model model){
        int studentSum = studentService.getAllCount();
        int teacherSum = teacherService.getAllCount();
        int courseSum = courseService.getAllCount();
        model.addAttribute("studentSum", studentSum);
        model.addAttribute("teacherSum", teacherSum);
        model.addAttribute("courseSum", courseSum);
        model.addAttribute("path", "index");
        return "admin/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("adminId");
        session.removeAttribute("adminName");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }
}
