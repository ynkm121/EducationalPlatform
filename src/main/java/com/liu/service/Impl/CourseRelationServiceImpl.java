package com.liu.service.Impl;

import com.liu.controller.student.vo.CourseRelationVO;
import com.liu.controller.student.vo.ScaleVO;
import com.liu.dao.CourseMapper;
import com.liu.dao.CourseRelationMapper;
import com.liu.dao.StudentMapper;
import com.liu.dao.TeacherMapper;
import com.liu.entity.*;
import com.liu.service.CourseRelationService;
import com.liu.service.DepartmentService;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseRelationServiceImpl implements CourseRelationService {

    @Autowired
    CourseRelationMapper relationMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    DepartmentService departmentService;

    @Override
    public PageResult getCourseRelationPage(PageQueryUtils utils) {
        List<CourseRelation> courseRelations = relationMapper.selectByStudentId(utils);
        if(courseRelations != null){
            List<CourseRelationVO> relationVOS = getCourseRelationVOByRelations(courseRelations);
            int count = relationMapper.selectCountSelective(utils);
            return new PageResult(count, utils.getLimit(), utils.getPage(), relationVOS);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean insertCourseByIds(Integer[] ids, Long studentId) {
        LinkedList<CourseRelation> relations = new LinkedList<>();
        for (Integer id : ids) {
            Course course = courseMapper.selectByPrimaryKey(id);
            course.setRemain(course.getRemain() - 1);
            courseMapper.updateSelective(course);
            CourseRelation relation = new CourseRelation();
            relation.setCourseId(id);
            relation.setStudentId(studentId);
            relation.setRelationTeacherId(course.getRelationTeacherId());
            relations.add(relation);
        }
        return relationMapper.BatchInsert(relations) > 0;
    }

    @Override
    public CourseRelation checkIfExistByIds(Long studentId, Integer courseId) {
        return relationMapper.selectByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public List<CourseRelation> getRelationByStudentId(Long studentId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("studentId", studentId);
        PageQueryUtils utils = new PageQueryUtils(params);
        return relationMapper.selectByStudentId(utils);
    }

    @Override
    public boolean checkPrereq(Long studentId, Integer courseId) {
        Course course = courseMapper.selectByPrimaryKey(courseId);
        String prerequisite = course.getPrerequisite();
        if(prerequisite != null){
            String[] prereqs = prerequisite.split(",");
            for (String prereq : prereqs) {
                int preCourseId = Integer.parseInt(prereq);
                CourseRelation relation = relationMapper.selectByStudentIdAndCourseId(studentId, preCourseId);
                if(relation == null || relation.getStudentScore() == -1){
                    return false;
                }
            }
        }
        return true;

    }

    @Override
    public PageResult getScalePages(PageQueryUtils utils) {
        List<CourseRelation> relations = relationMapper.selectForScalePages(utils);
        int count = relationMapper.selectCountSelective(utils);
        List<ScaleVO> vos = getScaleVOByRelations(relations);
        return new PageResult(count, utils.getLimit(), utils.getPage(), vos);
    }

    @Override
    public boolean updateScale(Long studentId, Integer courseId, Float finalNum) {
        return relationMapper.updateScale(studentId, courseId, finalNum) > 0;

    }


    private List<ScaleVO> getScaleVOByRelations(List<CourseRelation> relations) {
        List<ScaleVO> vos = new ArrayList<>();
        if(!relations.isEmpty()){
            List<Department> departments = departmentService.getAllDepartment(null);
            Map<Integer, String> departmentMap = departments.stream().collect(Collectors.toMap(Department::getDepartmentId, Department::getDepartmentName));
            for (CourseRelation relation : relations) {
                ScaleVO vo = new ScaleVO();
                Student student = studentMapper.getStudentById(relation.getStudentId());
                vo.setCourseId(relation.getCourseId());
                vo.setStudentId(student.getStudentId());
                vo.setStudentName(student.getStudentName());
                vo.setGrade(student.getStudentGrade());
                vo.setDepartment(departmentMap.get(student.getDepartment()));
                vos.add(vo);
            }
        }
        return vos;

    }


    public List<CourseRelationVO> getCourseRelationVOByRelations(List<CourseRelation> relations){
        if(relations != null){
            LinkedList<CourseRelationVO> relationVOS = new LinkedList<>();
            for(CourseRelation relation: relations){
                Teacher teacher = teacherMapper.selectByPrimaryKey(relation.getRelationTeacherId());
                Course course = courseMapper.selectByPrimaryKey(relation.getCourseId());
                CourseRelationVO relationVO = new CourseRelationVO();
                relationVO.setCourseId(course.getCourseId());
                relationVO.setCourseName(course.getCourseName());
                relationVO.setIntro(course.getIntro());
                relationVO.setTeacherName(teacher.getTeacherName());
                relationVO.setStudentScore(relation.getStudentScore());
                relationVOS.add(relationVO);
            }
            return relationVOS;
        }
        return null;
    }
}
