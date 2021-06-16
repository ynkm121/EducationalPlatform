package com.liu.service.Impl;

import com.liu.controller.student.vo.MessageVO;
import com.liu.dao.CourseMapper;
import com.liu.dao.CourseRelationMapper;
import com.liu.dao.MessageMapper;
import com.liu.dao.TeacherMapper;
import com.liu.entity.Course;
import com.liu.entity.CourseRelation;
import com.liu.entity.Message;
import com.liu.entity.Teacher;
import com.liu.service.CourseService;
import com.liu.service.MessageService;
import com.liu.util.PageQueryUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    CourseRelationMapper relationMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public boolean sendMessage(Message message) {
        int success = 0;
        success += messageMapper.insertSelective(message);
//        if(success == 1){
//            rabbitTemplate.convertAndSend("hello","",  "Hello,RabbitMQ!");
//        }
//        return success > 0;
        rabbitTemplate.convertAndSend("hello","Hello,RabbitMQ!");
        return true;
    }

    @Override
    public List<MessageVO> getAllforStudent(PageQueryUtils utils) {
        List<CourseRelation> relations = relationMapper.selectByStudentId(utils);
        ArrayList<Message> messagesAll = new ArrayList<>();
        for (CourseRelation relation : relations) {
            Integer courseId = relation.getCourseId();
            List<Message> messages = messageMapper.selectByCourseId(courseId);
            messagesAll.addAll(messages);
        }
        if(!messagesAll.isEmpty()){
            messagesAll.sort((o1, o2) -> {
                long gap = o1.getCreateTime().getTime() - o2.getCreateTime().getTime();
                if(gap > 0){
                    return -1;
                }else if (gap < 0){
                    return 1;
                }
                return 0;
            });
        }
        List<MessageVO> vos = getVOByMessages(messagesAll);
        return trimMessageLength(vos);
    }

    @Override
    public List<MessageVO> getSpecificMessage(Integer courseId) {
        List<Message> messages = messageMapper.selectByCourseId(courseId);
        if(!messages.isEmpty()){
            messages.sort((o1, o2) -> {
                long gap = o1.getCreateTime().getTime() - o2.getCreateTime().getTime();
                if(gap > 0){
                    return -1;
                }else if (gap < 0){
                    return 1;
                }
                return 0;
            });
        }
        List<MessageVO> vos = getVOByMessages(messages);
        return trimMessageLength(vos);
    }

    @Override
    public MessageVO getMessage(Integer messageId) {
        Message message = messageMapper.selectByPrimaryKey(messageId);
        ArrayList<Message> list = new ArrayList<>();
        list.add(message);
        List<MessageVO> vos = getVOByMessages(list);
        return vos.get(0);
    }

    private List<MessageVO> getVOByMessages(List<Message> messages) {
        ArrayList<MessageVO> vos = new ArrayList<>();
        if(messages != null){
            for (Message message : messages) {
                MessageVO vo = new MessageVO();
                vo.setMessageId(message.getMessageId());
                vo.setMessageBody(message.getMessageBody());
                Course course = courseMapper.selectByPrimaryKey(message.getCourseId());
                vo.setCourseName(course.getCourseName());
                Teacher teacher = teacherMapper.selectByPrimaryKey(message.getTeacherId());
                vo.setTeacherName(teacher.getTeacherName());
                vo.setGapTime(figureGapTime(message.getCreateTime()));
                vos.add(vo);
            }
        }
        return vos;
    }

    private String figureGapTime(Date createTime) {
        long gap = new Date().getTime() - createTime.getTime();
        long day = gap / (24 * 60 * 60 * 1000);
        long hour = (gap / (60 * 60 * 1000) - day * 24);
        long min = ((gap / (60 * 1000)) - day * 24 * 60 - hour * 60);
        StringBuilder sb = new StringBuilder();
        if(day != 0){
            sb.append(day).append("天前");
        }else if(hour != 0){
            sb.append(hour).append("小时前");
        }else{
            sb.append(min).append("分钟前");
        }
        return sb.toString();
    }

    private List<MessageVO> trimMessageLength(List<MessageVO> vos) {
        for (MessageVO message : vos) {
            String messageBody = message.getMessageBody();
            if(messageBody.length() > 15){
                String newBody = messageBody.substring(0, 15) + "...";
                message.setMessageBody(newBody);
            }
        }
        return vos;
    }


}
