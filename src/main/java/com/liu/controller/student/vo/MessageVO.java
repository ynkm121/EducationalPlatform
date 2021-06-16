package com.liu.controller.student.vo;

import lombok.Data;

@Data
public class MessageVO {
    private Integer messageId;

    private String teacherName;

    private String courseName;

    private String messageBody;

    private String gapTime;

}
