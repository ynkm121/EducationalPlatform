package com.liu.controller.teacher;

import com.liu.service.CourseService;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import com.liu.util.Result;
import com.liu.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class courseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/course")
    public String course(Model model){
        model.addAttribute("path", "course");
        return "teacher/course";
    }

    @PostMapping("/course/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params, HttpSession session){
        Integer teacherId = (Integer) session.getAttribute("teacherId");
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page")) || teacherId == null){
            return ResultGenerator.genFailResult("参数异常!");
        }
        params.put("teacherId", teacherId);
        PageQueryUtils utils = new PageQueryUtils(params);
        PageResult pageResult = courseService.getByPageUtils(utils);
        return ResultGenerator.genSuccessResult(pageResult);
    }
}
