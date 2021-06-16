package com.liu.controller.student;

import com.liu.controller.admin.vo.StudentVO;
import com.liu.entity.Student;
import com.liu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class profileController {
    @Autowired
    StudentService studentService;

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session){
        Long studentId = (Long) session.getAttribute("studentId");
        if(studentId == null){
            session.setAttribute("errMsg", "请先进行登录！");
            return "redirect:/login";
        }
        StudentVO studentVO = studentService.getStudentVO(studentId);
        model.addAttribute("student", studentVO);
        model.addAttribute("path", "profile");
        return "student/profile";
    }
}
