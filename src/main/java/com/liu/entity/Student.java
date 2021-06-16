package com.liu.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {


    private Long studentId;

      
    private String studentName;

      
    private Integer studentGrade;

      
    private String password;

      
    private Integer age;

      
    private Byte sex;

      
    private Integer department;

      
    private Byte isRegist;

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentGrade=" + studentGrade +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", department=" + department +
                ", isRegist=" + isRegist +
                ", isDelete=" + isDelete +
                ", loginTime=" + loginTime +
                ", createTime=" + createTime +
                '}';
    }

    private Byte isDelete;


    private Date loginTime;


    private Date createTime;


    private static final long serialVersionUID = 1L;


    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Long getStudentId() {
        return studentId;
    }

      
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

      
    public String getStudentName() {
        return studentName;
    }

      
    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

      
    public Integer getStudentGrade() {
        return studentGrade;
    }

      
    public void setStudentGrade(Integer studentGrade) {
        this.studentGrade = studentGrade;
    }

      
    public String getPassword() {
        return password;
    }

      
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

      
    public Integer getAge() {
        return age;
    }

      
    public void setAge(Integer age) {
        this.age = age;
    }

      
    public Byte getSex() {
        return sex;
    }

      
    public void setSex(Byte sex) {
        this.sex = sex;
    }

      
    public Integer getDepartment() {
        return department;
    }

      
    public void setDepartment(Integer department) {
        this.department = department;
    }

      
    public Byte getIsRegist() {
        return isRegist;
    }

      
    public void setIsRegist(Byte isRegist) {
        this.isRegist = isRegist;
    }

      
    public Date getLoginTime() {
        return loginTime;
    }

      
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

      
    public Date getCreateTime() {
        return createTime;
    }

      
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}