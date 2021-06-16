package com.liu.controller.security.Realms;

import com.liu.entity.Teacher;
import com.liu.service.TeacherService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherRealm extends AuthorizingRealm {
    @Autowired
    TeacherService teacherService;

    public String getName(){
        return "TeacherRealm";
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("老师验证");
        Integer teacherId = Integer.parseInt((String)authenticationToken.getPrincipal());
        Teacher teacher = teacherService.getTeacherById(teacherId);
        if(teacher == null || teacher.getIsDelete() == 1){
            throw new UnknownAccountException("用户名或密码不正确");
        }
        if(teacher.getIsRegist() == 0){
            throw new UnknownAccountException("你尚未通过注册，请耐心等待管理员审核");
        }

        return new SimpleAuthenticationInfo(teacher.getTeacherId(), teacher.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("Teacher");
        return info;
    }
}
