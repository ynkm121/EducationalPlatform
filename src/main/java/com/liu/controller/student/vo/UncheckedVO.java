package com.liu.controller.student.vo;

import lombok.Data;

@Data
public class UncheckedVO {
    private Integer courseId;

    private String courseName;

    private String teacherName;

    private int remain;

    private String intro;
}
