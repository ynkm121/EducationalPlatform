package com.liu.controller.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class StudentVO {

    private Long studentId;


    private String studentName;


    private Integer studentGrade;


    private String password;


    private Integer age;


    private Byte sex;


    private String departmentName;


    private Byte isRegist;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
