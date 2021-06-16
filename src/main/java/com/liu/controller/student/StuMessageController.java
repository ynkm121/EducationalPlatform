package com.liu.controller.student;

import com.liu.controller.student.vo.CourseRelationVO;
import com.liu.controller.student.vo.MessageVO;
import com.liu.entity.Message;
import com.liu.service.CourseRelationService;
import com.liu.service.MessageService;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import com.liu.util.Result;
import com.liu.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StuMessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    CourseRelationService relationService;

    @GetMapping("/message")
    public String message(Model model, HttpSession session){
        Long studentId = (Long) session.getAttribute("studentId");
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", 1);
        map.put("limit", 50);
        map.put("studentId", studentId);
        PageQueryUtils utils = new PageQueryUtils(map);
        List<MessageVO> messages = messageService.getAllforStudent(utils);
        PageResult pageResult = relationService.getCourseRelationPage(utils);
        List<CourseRelationVO> courses = (List<CourseRelationVO>)pageResult.getList();
        model.addAttribute("courses", courses);
        model.addAttribute("count", messages.size());
        model.addAttribute("path", "message");
        model.addAttribute("messages", messages);
        return "student/message";
    }

    @GetMapping("/message/course")
    public String sortCourse(@RequestParam("courseId")Integer courseId, Model model, HttpSession session){
        if(courseId == null){
            return message(model, session);
        }

        List<MessageVO> messages = messageService.getSpecificMessage(courseId);
        model.addAttribute("count", messages.size());
        model.addAttribute("path", "message");
        model.addAttribute("messages", messages);
        return "student/messageForm";
    }

    @GetMapping("/message/{messageId}")
    public String detail(@PathVariable("messageId")Integer messageId, Model model){
        MessageVO message = messageService.getMessage(messageId);
        model.addAttribute("message", message);
        model.addAttribute("path", "message");
        return "student/messageDetail";
    }
}
