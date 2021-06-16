package com.liu.controller.student;

import com.liu.controller.student.vo.CourseRelationVO;
import com.liu.service.CourseRelationService;
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
public class selectController {
    @Autowired
    CourseRelationService courseRelationService;

    @GetMapping("/select")
    public String select(Model model){
        model.addAttribute("path", "select");
        return "student/select";
    }

    @PostMapping("/select/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params,
                       HttpSession session){
        //TODO
        Long studentId = (Long)session.getAttribute("studentId");
        if(StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("page")) || studentId == null){
            return ResultGenerator.genFailResult("参数异常!");
        }
        params.put("studentId", studentId);
        PageQueryUtils utils = new PageQueryUtils(params);
        PageResult pageResult = courseRelationService.getCourseRelationPage(utils);
        if(pageResult != null){
            return ResultGenerator.genSuccessResult(pageResult);
        }
        return ResultGenerator.genFailResult("操作错误!");
    }

}
