package com.liu.controller.student.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRelationVO implements Serializable {

    private Integer courseId;

    private String courseName;

    private String intro;

    private Float studentScore;

    private String TeacherName;

}
