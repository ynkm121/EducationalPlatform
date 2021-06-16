package com.liu.controller.student;

import com.liu.entity.Course;
import com.liu.entity.CourseRelation;
import com.liu.service.CourseRelationService;
import com.liu.service.CourseService;
import com.liu.service.UncheckedService;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import com.liu.util.Result;
import com.liu.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class uncheckedController {
    @Autowired
    UncheckedService uncheckedService;
    @Autowired
    CourseRelationService relationService;
    @Autowired
    CourseService courseService;

    @GetMapping("/unchecked")
    public String unchecked(Model model){
        model.addAttribute("path", "select unchecked");
        return "student/unchecked";
    }

    @PostMapping("/unchecked/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params, HttpSession session){
        Long studentId = (Long)session.getAttribute("studentId");
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page")) || studentId == null){
            return ResultGenerator.genFailResult("参数异常!");
        }
        params.put("studentId", studentId);
        PageQueryUtils utils = new PageQueryUtils(params);
        PageResult uncheckedPage = uncheckedService.getUncheckedPage(utils);
        return ResultGenerator.genSuccessResult(uncheckedPage);
    }

    @PostMapping("/unchecked/select")
    @ResponseBody
    public Result select(@RequestBody Integer[] courseIds, HttpSession session){
        Long studentId = (Long) session.getAttribute("studentId");
        if(courseIds.length < 1 || studentId == null){
            return ResultGenerator.genFailResult("参数异常");
        }
        for (Integer courseId : courseIds) {
            if(!relationService.checkPrereq(studentId, courseId)){
                Course course = courseService.getByCourseId(courseId);
                return ResultGenerator.genFailResult("您选修的" + course.getCourseName() + "的先修课条件尚未达到!");
            }
        }
        if(relationService.insertCourseByIds(courseIds, studentId)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作错误");
        }
    }
}
