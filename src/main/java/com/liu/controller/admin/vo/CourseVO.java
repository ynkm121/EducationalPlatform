package com.liu.controller.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CourseVO {
    private Integer courseId;

    private String courseName;

    private String teacherName;

    private String prerequisite;

    private int remain;

    private String intro;

    private Integer credit;

    private Integer selectCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
