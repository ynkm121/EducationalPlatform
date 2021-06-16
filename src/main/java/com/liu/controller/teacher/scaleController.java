package com.liu.controller.teacher;

import com.liu.entity.Course;
import com.liu.service.CourseRelationService;
import com.liu.service.CourseService;
import com.liu.service.StudentService;
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
@RequestMapping("/teacher")
public class scaleController {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseRelationService relationService;

    @GetMapping("/scale")
    public String scale(Model model){
        model.addAttribute("path", "course");
        model.addAttribute("coursewareErrMsg", "请选择要评分的课程");
        return "teacher/course";
    }

    @GetMapping("/scale/{courseId}")
    public String scale(@PathVariable("courseId")Integer courseId, Model model, HttpSession session){
        Course course = courseService.getByCourseId(courseId);
        Integer teacherId = (Integer) session.getAttribute("teacherId");
        if(!course.getRelationTeacherId().equals(teacherId)){
            model.addAttribute("path", "course");
            model.addAttribute("coursewareErrMsg", "您尚未担任此课程");
            return "teacher/course";
        }
        session.setAttribute("course", course);
        model.addAttribute("path", "course scale");
        return "teacher/scale";
    }

    @PostMapping("/scale/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params, HttpSession session){
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page"))){
            return ResultGenerator.genFailResult("参数异常!");
        }
        Course course = (Course) session.getAttribute("course");
        Integer teacherId = (Integer) session.getAttribute("teacherId");
        params.put("teacherId", teacherId);
        params.put("courseId", course.getCourseId());
        PageQueryUtils utils = new PageQueryUtils(params);
        if (course != null) {
            PageResult pageResult = relationService.getScalePages(utils);
            return ResultGenerator.genSuccessResult(pageResult);
        }else{
            return ResultGenerator.genFailResult("没有获取到课程id!");
        }
    }

    @PostMapping("/scale/save")
    @ResponseBody
    public Result save(@RequestParam("studentId")Long studentId,
                       @RequestParam("perform")Integer perform,
                       @RequestParam("final")Integer finalExam,
                       HttpSession session){
        if(studentId == null || perform == null || finalExam == null){
            return ResultGenerator.genFailResult("参数异常");
        }
        Course course = (Course) session.getAttribute("course");
        Float finalNum = (float)(perform * 0.4 + finalExam * 0.6);
        if(relationService.updateScale(studentId, course.getCourseId(), finalNum)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作失败，请联系管理员");
        }
    }

}
