package com.liu.controller.teacher;

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
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherwareController {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseRelationService relationService;
    @Autowired
    CoursewareService coursewareService;

    @GetMapping("/courseware")
    public String courseware(Model model){
        model.addAttribute("path", "course");
        model.addAttribute("coursewareErrMsg", "请选择要查看的课程");
        return "teacher/course";
    }

    @GetMapping("/courseware/{courseId}")
    public String index(@PathVariable("courseId")String courseIdString, Model model, HttpSession session){
        int courseId = 0;
        try {
            courseId = Integer.parseInt(courseIdString);
        } catch (NumberFormatException e) {
            model.addAttribute("coursewareErrMsg", "参数错误");
            model.addAttribute("path", "course");
            return "teacher/course";
        }
        Course course = courseService.getByCourseId(courseId);
        if(course == null){
            model.addAttribute("path", "course");
            model.addAttribute("coursewareErrMsg", "您尚未开启教授该课程，请重新选择");
            return "teacher/course";
        }
        Integer teacherId = (Integer) session.getAttribute("teacherId");
        if(!teacherId.equals(course.getRelationTeacherId())){
            model.addAttribute("path", "course");
            model.addAttribute("coursewareErrMsg", "您尚未开启教授该课程，请重新选择");
            return "teacher/course";
        }else{
            model.addAttribute("path", "courseware");
            model.addAttribute("courseName", course.getCourseName());
            session.setAttribute("courseId", courseId);
            return "teacher/courseware";
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

    @PostMapping("/courseware/save")
    @ResponseBody
    public Result save(@RequestParam("courseware_intro")String intro,
                       @RequestParam("courseware_url")String url,
                       HttpSession session){
        if(StringUtils.isEmpty(intro) || StringUtils.isEmpty(url)){
            return ResultGenerator.genFailResult("参数异常");
        }
        int courseId = (int) session.getAttribute("courseId");
        Courseware courseware = new Courseware();
        courseware.setCoursewareIntro(intro);
        courseware.setCoursewareUrl(url);
        courseware.setCourseId(courseId);
        if(coursewareService.insertCourseware(courseware)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作错误");
        }

    }
}
