package com.liu.controller.admin;

import com.liu.entity.Course;
import com.liu.entity.Teacher;
import com.liu.service.CourseService;
import com.liu.service.TeacherService;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import com.liu.util.Result;
import com.liu.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminCourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;

    @GetMapping("/course")
    public String index(Model model){
        model.addAttribute("path", "course");
        return "admin/course";
    }

    @PostMapping("/course/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params){
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page"))){
            return ResultGenerator.genFailResult("参数异常!");
        }
        PageQueryUtils utils = new PageQueryUtils(params);
        PageResult pageResult = courseService.getByPageUtilsForAdmin(utils);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    @GetMapping("/course/teacherInfo")
    @ResponseBody
    public Result teacherInfo(){
        List<String> teachers = teacherService.getAllTeacherName();
        return ResultGenerator.genSuccessResult(teachers);
    }

    @GetMapping("/course/courseInfo")
    @ResponseBody
    public Result courseInfo(){
        List<Course> courses = courseService.getAll();
        return ResultGenerator.genSuccessResult(courses);
    }

    @PostMapping("/course/save")
    @ResponseBody
    public Result save(@RequestParam("courseName")String courseName,
                       @RequestParam("teacherName")String teacherName,
                       @RequestParam("prerequisite")List<String> prerequisites,
                       @RequestParam("intro")String intro,
                       @RequestParam("credit")Integer credit,
                       @RequestParam("remain")Integer remain){
        if(StringUtils.isEmpty(courseName) || StringUtils.isEmpty(teacherName) || StringUtils.isEmpty(intro) || credit <= 0 || remain <= 0){
            return ResultGenerator.genFailResult("参数异常");
        }
        Teacher teacher = teacherService.getTeacherByName(teacherName);
        Course course = new Course();
        course.setCourseName(courseName);
        course.setRemain(remain);
        course.setCredit(credit);
        course.setIntro(intro);
        course.setRelationTeacherId(teacher.getTeacherId());
        if(prerequisites.size() != 1 || !prerequisites.get(0).equals("0")){
            course.setPrerequisite(String.join("," , prerequisites));
        }else {
            course.setPrerequisite(null);
        }
            if(courseService.insertCourse(course)){
            return ResultGenerator.genSuccessResult("成功");
        }else{
            return ResultGenerator.genFailResult("操作失败");
        }
    }

    @PostMapping("/course/update")
    @ResponseBody
    public Result update(@RequestParam("courseId")Integer courseId,
                        @RequestParam("courseName")String courseName,
                       @RequestParam("teacherName")String teacherName,
                       @RequestParam("prerequisite")List<String> prerequisites,
                       @RequestParam("intro")String intro,
                       @RequestParam("credit")Integer credit,
                       @RequestParam("remain")Integer remain){
        if(StringUtils.isEmpty(courseName) || StringUtils.isEmpty(teacherName) || StringUtils.isEmpty(intro) || credit <= 0 || remain <= 0){
            return ResultGenerator.genFailResult("参数异常");
        }
        Teacher teacher = teacherService.getTeacherByName(teacherName);
        Course course = courseService.getByCourseId(courseId);
        course.setCourseName(courseName);
        course.setRemain(remain);
        course.setCredit(credit);
        course.setIntro(intro);
        course.setRelationTeacherId(teacher.getTeacherId());
        if(prerequisites.size() != 1 || !prerequisites.get(0).equals("0")){
            course.setPrerequisite(String.join("," , prerequisites));
        }else{
            course.setPrerequisite(null);
        }
        if(courseService.updateCourse(course)){
            return ResultGenerator.genSuccessResult("成功");
        }else{
            return ResultGenerator.genFailResult("操作失败");
        }
    }

    @PostMapping("/course/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if(ids.length < 1){
            return ResultGenerator.genFailResult("参数异常");
        }
        if(courseService.BatchDelete(ids)){
            return ResultGenerator.genSuccessResult("操作成功");
        }else{
            return ResultGenerator.genFailResult("操作失败");
        }
    }
}
