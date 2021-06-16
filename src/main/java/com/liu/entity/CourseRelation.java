package com.liu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CourseRelation implements Serializable {
    private Integer id;

    private Integer courseId;

    private Long studentId;

    private Integer relationTeacherId;

    private Float studentScore;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}