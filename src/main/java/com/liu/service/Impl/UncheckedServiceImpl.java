package com.liu.service.Impl;

import com.liu.controller.admin.vo.CourseVO;
import com.liu.dao.CourseMapper;
import com.liu.dao.CourseRelationMapper;
import com.liu.dao.StudentMapper;
import com.liu.dao.TeacherMapper;
import com.liu.entity.Course;
import com.liu.entity.CourseRelation;
import com.liu.entity.Student;
import com.liu.entity.Teacher;
import com.liu.service.UncheckedService;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UncheckedServiceImpl implements UncheckedService {
    @Autowired
    CourseRelationMapper relationMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public PageResult getUncheckedPage(PageQueryUtils utils) {
        List<CourseRelation> relations = relationMapper.selectByStudentId(utils);
        List<CourseVO> CourseVOS = getVOSByRelation(relations);
        int count = courseMapper.selectCount(null) - relations.size();
        return new PageResult(count, utils.getLimit(), utils.getPage(), CourseVOS);
    }

    private List<CourseVO> getVOSByRelation(List<CourseRelation> relations) {
        LinkedList<CourseVO> VOlists = new LinkedList<>();
        List<Course> uncheckedCourse;
        if(relations.isEmpty()){
            uncheckedCourse = courseMapper.selectAll(null);
        }else{
            List<Integer> courseId = relations.stream().map(CourseRelation::getCourseId).collect(Collectors.toList());
            uncheckedCourse = courseMapper.selectUncheckedbyCourseId(courseId);
        }
        for (Course course : uncheckedCourse) {
            CourseVO vo = new CourseVO();
            vo.setCourseId(course.getCourseId());
            vo.setCourseName(course.getCourseName());
            vo.setIntro(course.getIntro());
            vo.setRemain(course.getRemain());
            vo.setCredit(course.getCredit());
            Teacher teacher = teacherMapper.selectByPrimaryKey(course.getRelationTeacherId());
            vo.setTeacherName(teacher.getTeacherName());
            vo.setPrerequisite(getPrerequisite(course.getPrerequisite()));
            VOlists.add(vo);
        }
        return VOlists;
    }

    private String getPrerequisite(String prerequisite) {
        if(!StringUtils.isEmpty(prerequisite)){
            String[] courses = prerequisite.split(",");
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i <courses.length; i++){
                int courseId = Integer.parseInt(courses[i]);
                Course course = courseMapper.selectByPrimaryKey(courseId);
                sb.append(course.getCourseName());
                if(i != courses.length - 1){
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        return null;
    }
}
