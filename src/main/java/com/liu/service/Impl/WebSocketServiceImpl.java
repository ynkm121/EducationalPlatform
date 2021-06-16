package com.liu.service.Impl;

import com.liu.dao.CourseMapper;
import com.liu.dao.CourseRelationMapper;
import com.liu.dao.TeacherMapper;
import com.liu.entity.Course;
import com.liu.entity.CourseRelation;
import com.liu.entity.Teacher;
import com.liu.model.OutMessage;
import com.liu.model.TeacherMessage;
import com.liu.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @Autowired
    CourseRelationMapper relationMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public void sendMessage(TeacherMessage message) {
        int courseId = message.getCourseId();
        int teacherId = message.getTeacherId();
        String content = message.getMessage();
        //查出所有选修该课程的学生
        List<CourseRelation> relations = relationMapper.selectByCourseId(courseId);
        //获得课程名称
        Course course = courseMapper.selectByPrimaryKey(courseId);
        //获得教师名称
        Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);
        List<Long> studentIds = relations.stream().map(CourseRelation::getStudentId).collect(Collectors.toList());
        for (Long studentId : studentIds) {
            messagingTemplate.convertAndSend("/message/student/" + studentId,
                    new OutMessage(teacher.getTeacherName(), course.getCourseName(), content));
            System.out.println("向" + studentId + "发送了一条消息");
        }
    }
}
