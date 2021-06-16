package com.liu.service;

import com.liu.controller.student.vo.MessageVO;
import com.liu.entity.Message;
import com.liu.util.PageQueryUtils;

import java.util.List;

public interface MessageService {

    boolean sendMessage(Message message);

    List<MessageVO> getAllforStudent(PageQueryUtils utils);

    List<MessageVO> getSpecificMessage(Integer courseId);

    MessageVO getMessage(Integer messageId);
}
