package com.liu.controller;

import com.liu.util.CommonUtils;
import com.liu.util.Result;
import com.liu.util.ResultGenerator;
import com.liu.util.constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

@Controller
public class FileController {

    @GetMapping("/upload/{filename}")
    @ResponseBody
    public void download(@PathVariable("filename")String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        File path = new File(constant.FILE_DIC + File.separator + filename);
        if(!path.exists()){
            return ;
        }
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream;charset=UTF-8");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        try {
            FileInputStream fis = new FileInputStream(path);
            byte[] content = new byte[fis.available()];
            fis.read(content);
            fis.close();

            ServletOutputStream sos = response.getOutputStream();
            sos.write(content);

            sos.flush();
            sos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("teacher/upload/courseware")
    @ResponseBody
    public Result upload(HttpServletRequest request, @RequestParam("courseware")MultipartFile file, Model model){
        String filename = file.getOriginalFilename();
        String suffixName = filename.substring(filename.lastIndexOf('.'));
        File destFile = new File(constant.FILE_DIC + filename);
        try{
            if(!destFile.getParentFile().exists()){
                destFile.getParentFile().mkdirs();
            }
            file.transferTo(destFile);
            model.addAttribute("attachName", filename);
            Result result = ResultGenerator.genSuccessResult();
            result.setData(CommonUtils.getHost(new URI(String.valueOf(request.getRequestURI()))) + "/upload" + filename);
            result.setMessage(filename);
            return result;
        }catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }

}
