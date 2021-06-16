package com.liu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.util.Date;

public class Courseware implements Serializable {
    private Integer courseId;

    private String coursewareIntro;

    private String coursewareUrl;

    private Byte isDeleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Byte getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Byte deleted) {
        isDeleted = deleted;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCoursewareIntro() {
        return coursewareIntro;
    }

    public void setCoursewareIntro(String coursewareIntro) {
        this.coursewareIntro = coursewareIntro == null ? null : coursewareIntro.trim();
    }

    public String getCoursewareUrl() {
        return coursewareUrl;
    }

    public void setCoursewareUrl(String coursewareUrl) {
        this.coursewareUrl = coursewareUrl == null ? null : coursewareUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Courseware{" +
                "courseId=" + courseId +
                ", coursewareIntro='" + coursewareIntro + '\'' +
                ", coursewareUrl='" + coursewareUrl + '\'' +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}