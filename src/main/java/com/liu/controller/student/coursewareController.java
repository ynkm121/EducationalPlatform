package com.liu.controller.student;

import com.liu.entity.Course;
import com.liu.entity.CourseRelation;
import com.liu.entity.Courseware;
import com.liu.service.CourseRelationService;
import com.liu.service.CourseService;
import com.liu.service.CoursewareService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class coursewareController {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseRelationService relationService;
    @Autowired
    CoursewareService coursewareService;

    @GetMapping("/courseware")
    public String courseware(Model model){
        model.addAttribute("path", "select");
        model.addAttribute("coursewareErrMsg", "请选择要查看的课程");
        return "student/select";
    }

    @GetMapping("/courseware/{courseId}")
    public String index(@PathVariable("courseId")String courseIdString, Model model, HttpSession session){
        int courseId = 0;
        try {
            courseId = Integer.parseInt(courseIdString);
        } catch (NumberFormatException e) {
            model.addAttribute("coursewareErrMsg", "参数错误");
            model.addAttribute("path", "select");
            return "student/select";
        }
        Course course = courseService.getByCourseId(courseId);
        if(course == null){
            model.addAttribute("path", "select");
            model.addAttribute("coursewareErrMsg", "您尚未选修该课程，请重新选择");
            return "student/select";
        }
        Long studentId = (Long) session.getAttribute("studentId");
        CourseRelation relation = relationService.checkIfExistByIds(studentId, courseId);
        if(relation == null){
            model.addAttribute("path", "select");
            model.addAttribute("coursewareErrMsg", "您尚未选修该课程，请重新选择");
            return "student/select";
        }else{
            model.addAttribute("path", "courseware");
            model.addAttribute("courseName", course.getCourseName());
            session.setAttribute("courseId", courseId);
            return "student/courseware";
        }
    }

    @PostMapping("/courseware/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params, HttpSession session){
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page"))){
            ResultGenerator.genFailResult("参数异常!");
        }
        Integer courseId = (Integer) session.getAttribute("courseId");
        params.put("courseId", courseId);
        PageQueryUtils utils = new PageQueryUtils(params);
        PageResult result = coursewareService.getWareByCourseId(utils);
        return ResultGenerator.genSuccessResult(result);
    }


}
