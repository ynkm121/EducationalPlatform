package com.liu.controller.teacher;

import com.liu.dao.MessageMapper;
import com.liu.entity.Course;
import com.liu.entity.Message;
import com.liu.service.CourseService;
import com.liu.service.MessageService;
import com.liu.util.Result;
import com.liu.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class messageController {
    @Autowired
    CourseService courseService;
    @Autowired
    MessageService messageService;

    @GetMapping("/message")
    public String course(Model model, HttpSession session){
        Integer teacherId = (Integer) session.getAttribute("teacherId");
        List<Course> courses = courseService.getByTeacherId(teacherId);
        model.addAttribute("courses", courses);
        model.addAttribute("path", "message");
        return "teacher/message";
    }

    @PostMapping("/message/send")
    @ResponseBody
    public Result send(@RequestParam("courseId")Integer courseId,
                       @RequestParam("message")String messageBody,
                       HttpSession session){
        if(courseId == null || StringUtils.isEmpty(messageBody)){
            return ResultGenerator.genFailResult("参数异常");
        }
        Integer teacherId = (Integer) session.getAttribute("teacherId");
        Message message = new Message();
        message.setMessageBody(messageBody);
        message.setCourseId(courseId);
        message.setTeacherId(teacherId);
        if(messageService.sendMessage(message)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作错误");
        }
    }
}
