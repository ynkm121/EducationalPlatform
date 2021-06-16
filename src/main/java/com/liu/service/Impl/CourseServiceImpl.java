package com.liu.service.Impl;

import com.liu.controller.admin.vo.CourseVO;
import com.liu.dao.CourseMapper;
import com.liu.dao.CourseRelationMapper;
import com.liu.dao.TeacherMapper;
import com.liu.entity.Course;
import com.liu.service.CourseService;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    CourseRelationMapper relationMapper;

    @Override
    public Course getByCourseId(Integer courseId) {
        return courseMapper.selectByPrimaryKey(courseId);
    }

    @Override
    public PageResult getByPageUtils(PageQueryUtils utils) {
        List<Course> courses = courseMapper.selectAll(utils);
        int count = courseMapper.selectCount(null);
        return new PageResult(count, utils.getLimit(), utils.getPage(), courses);
    }

    @Override
    public PageResult getByPageUtilsForAdmin(PageQueryUtils utils) {
        List<Course> courses = courseMapper.selectAll(utils);
        int count = courseMapper.selectCount(null);
        List<CourseVO> courseVOS = getVOByCourses(courses);
        return new PageResult(count, utils.getLimit(), utils.getPage(), courseVOS);
    }

    @Override
    public boolean insertCourse(Course course) {
        return courseMapper.insert(course) > 0;
    }

    @Override
    public boolean BatchDelete(Integer[] ids) {
        return courseMapper.BatchDelete(ids) > 0;
    }

    @Override
    public boolean updateCourse(Course course) {
        return courseMapper.updateSelective(course) > 0;
    }

    @Override
    public List<Course> getByTeacherId(Integer teacherId) {
        return courseMapper.selectByTeacherId(teacherId);
    }

    @Override
    public int getAllCount() {
        return courseMapper.selectCount(null);
    }

    @Override
    public List<Course> getAll() {
        return courseMapper.selectAll(null);
    }

    private List<CourseVO> getVOByCourses(List<Course> courses) {
        LinkedList<CourseVO> courseVOS = new LinkedList<>();
        if(courses != null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("page", 1);
            map.put("limit", 1024);
            PageQueryUtils utils = new PageQueryUtils(map);
            for (Course course : courses) {
                utils.put("courseId", course.getCourseId());
                int selectCount = relationMapper.selectCountSelective(utils);
                CourseVO vo = new CourseVO();
                vo.setCourseId(course.getCourseId());
                vo.setCourseName(course.getCourseName());
                vo.setCreateTime(course.getCreateTime());
                vo.setCredit(course.getCredit());
                vo.setIntro(course.getIntro());
                vo.setRemain(course.getRemain());
                vo.setSelectCount(selectCount);
                vo.setTeacherName(teacherMapper.selectByPrimaryKey(course.getRelationTeacherId()).getTeacherName());
                vo.setPrerequisite(getPrerequisite(course.getPrerequisite()));
                courseVOS.add(vo);
                utils.remove("courseId");
            }
        }
        return courseVOS;

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
