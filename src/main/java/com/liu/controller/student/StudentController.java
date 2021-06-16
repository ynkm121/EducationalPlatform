package com.liu.controller.student;

import com.liu.entity.Course;
import com.liu.entity.CourseRelation;
import com.liu.service.CourseRelationService;
import com.liu.service.CourseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    CourseRelationService relationService;
    @Autowired
    CourseService courseService;

    @GetMapping({"", "/", "index", "index.html"})
    public String index(Model model, HttpSession session){
        Long studentId = (Long)session.getAttribute("studentId");
        List<CourseRelation> relations = relationService.getRelationByStudentId(studentId);
        int creditSum = 0;
        List<Course> courses = new ArrayList<>();
        for (CourseRelation relation : relations) {
            Course course = courseService.getByCourseId(relation.getCourseId());
            courses.add(course);
            creditSum += course.getCredit();
        }
        model.addAttribute("courseCount", relations.size());
        model.addAttribute("courses", courses);
        model.addAttribute("credit", creditSum);
        model.addAttribute("path", "index");
        return "student/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("studentId");
        session.removeAttribute("studentName");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

    @GetMapping("/websocket")
    public String websocket(){
        return "student/websocket";
    }
}
