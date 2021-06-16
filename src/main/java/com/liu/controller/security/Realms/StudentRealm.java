package com.liu.controller.security.Realms;

import com.liu.entity.Student;
import com.liu.service.StudentService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentRealm extends AuthorizingRealm {
    @Autowired
    StudentService studentService;

    @Override
    public String getName() {
        return "StudentRealm";
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Long studentId = Long.parseLong((String)authenticationToken.getPrincipal());
        System.out.println("学生验证" + studentId);
        Student student = studentService.getStudentById(studentId);
        if(student == null || student.getIsDelete() == 1){
            throw new UnknownAccountException("用户名或密码不正确");
        }
        if(student.getIsRegist() == 0){
            throw new UnknownAccountException("你尚未通过注册，请耐心等待管理员审核");
        }

        return new SimpleAuthenticationInfo(student.getStudentId(), student.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("Student");
        return info;
    }
}
