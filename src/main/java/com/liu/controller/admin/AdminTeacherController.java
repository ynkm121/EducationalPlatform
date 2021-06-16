package com.liu.controller.admin;

import com.liu.entity.Department;
import com.liu.entity.Student;
import com.liu.entity.Teacher;
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
public class AdminTeacherController {
    @Autowired
    TeacherService teacherService;

    @GetMapping("/teacher")
    public String index(Model model){
        model.addAttribute("path", "teacher");
        return "admin/teacher";
    }

    @PostMapping("/teacher/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params){
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page"))){
            return ResultGenerator.genFailResult("参数异常!");
        }
        params.put("isRegist", 1);
        PageQueryUtils utils = new PageQueryUtils(params);
        PageResult pageResult = teacherService.getTeacherPages(utils);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    @PostMapping("/teacher/regist/list")
    @ResponseBody
    public Result unregist(@RequestParam Map<String, Object> params){
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page"))){
            return ResultGenerator.genFailResult("参数异常!");
        }
        params.put("isRegist", 0);
        PageQueryUtils utils = new PageQueryUtils(params);
        PageResult pageResult = teacherService.getTeacherPages(utils);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    @PostMapping("/teacher/save")
    @ResponseBody
    public Result save(@RequestParam("teacherName")String teacherName,
                       @RequestParam("password")String password){
        if(StringUtils.isEmpty(teacherName) ||StringUtils.isEmpty(password)){
            return ResultGenerator.genFailResult("参数异常");
        }
        Teacher teacher = new Teacher();
        teacher.setTeacherName(teacherName);
        teacher.setPassword(password);
        teacher.setIsRegist((byte)1);
        if(teacherService.insertTeacher(teacher)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作错误");
        }
    }

    @PostMapping("/teacher/update")
    @ResponseBody
    public Result update(@RequestParam("teacherId")Integer teacherId,
                         @RequestParam("teacherName")String teacherName,
                         @RequestParam("password")String password){
        if(StringUtils.isEmpty(teacherName) ||StringUtils.isEmpty(password)){
            return ResultGenerator.genFailResult("参数异常");
        }
        Teacher teacher = teacherService.getTeacherById(teacherId);
        teacher.setTeacherName(teacherName);
        teacher.setPassword(password);
        teacher.setIsRegist((byte)1);
        if(teacherService.updateTeacher(teacher)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作错误");
        }
    }

    @PostMapping("/teacher/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if(ids.length < 1){
            return ResultGenerator.genFailResult("参数异常");
        }
        if(teacherService.BatchDelete(ids)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作失败");
        }
    }

    @PostMapping("/teacher/confirm")
    @ResponseBody
    public Result confirm(@RequestBody Integer[] ids){
        if(ids.length < 1){
            return ResultGenerator.genFailResult("参数异常");
        }
        if(teacherService.BatchConfirm(ids)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("操作失败");
        }
    }
}
