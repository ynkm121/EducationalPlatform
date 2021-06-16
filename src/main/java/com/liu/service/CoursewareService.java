package com.liu.service;

import com.liu.entity.Courseware;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;

import java.util.List;

public interface CoursewareService {

    PageResult getWareByCourseId(PageQueryUtils utils);

    boolean insertCourseware(Courseware courseware);
}
