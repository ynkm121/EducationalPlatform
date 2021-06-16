package com.liu.service.Impl;

import com.liu.dao.CoursewareMapper;
import com.liu.entity.Courseware;
import com.liu.service.CoursewareService;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursewareServiceImpl implements CoursewareService {
    @Autowired
    CoursewareMapper coursewareMapper;

    @Override
    public PageResult getWareByCourseId(PageQueryUtils utils) {
        List<Courseware> coursewares = coursewareMapper.selectByCourseId(utils);
        int count = coursewareMapper.selectCount(null);
        return new PageResult(count, utils.getLimit(), utils.getPage(), coursewares);
    }

    @Override
    public boolean insertCourseware(Courseware courseware) {
        return coursewareMapper.insert(courseware) > 0;
    }
}
