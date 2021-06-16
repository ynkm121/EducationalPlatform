package com.liu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Course implements Serializable {
    private Integer courseId;

    private String courseName;

    private Integer relationTeacherId;

    private String prerequisite;

    private String intro;

    private Integer credit;

    private Integer remain;

    private Byte isDeleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}