package com.liu.controller.teacher;

import com.liu.entity.Course;
import com.liu.service.CourseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    CourseService courseService;

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(Model model, HttpSession session){
        Integer teacherId = (Integer) session.getAttribute("teacherId");
        List<Course> courses = courseService.getByTeacherId(teacherId);
        model.addAttribute("courseCount", courses.size());
        model.addAttribute("courses", courses);
        model.addAttribute("path", "index");
        return "teacher/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("teacherId");
        session.removeAttribute("teacherName");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }
}
