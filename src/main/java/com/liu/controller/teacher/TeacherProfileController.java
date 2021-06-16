package com.liu.controller.teacher;

import com.liu.entity.Teacher;
import com.liu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/teacher")
public class TeacherProfileController {
    @Autowired
    TeacherService teacherService;

    @GetMapping("/profile")
    public String course(Model model, HttpSession session){
        Integer teacherId = (Integer) session.getAttribute("teacherId");
        if(teacherId == null){
            session.setAttribute("errMsg", "请先登录");
        }
        Teacher teacher = teacherService.getTeacherById(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("path", "profile");
        return "teacher/profile";
    }
}
