package com.liu.dao;

import com.liu.entity.Message;
import com.liu.util.PageQueryUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface MessageMapper {

    int insert(Message record);

    Message selectByPrimaryKey(Integer messageId);

    List<Message> selectAll(PageQueryUtils utils);

    int insertSelective(Message message);

    List<Message> selectByCourseId(Integer courseId);
}