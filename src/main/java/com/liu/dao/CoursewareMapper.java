package com.liu.dao;

import com.liu.entity.Courseware;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CoursewareMapper {
    int insert(Courseware record);

    List<Courseware> selectAll();

    List<Courseware> selectByCourseId(PageQueryUtils utils);

    int selectCount(PageQueryUtils utils);
}