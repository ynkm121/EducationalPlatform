package com.liu.controller.security.Realms;

import com.liu.entity.Student;
import com.liu.service.StudentService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    StudentService studentService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Student student = studentService.getStudentById(Long.parseLong(token.getUsername()));
        if(student == null){
            throw new UnknownAccountException("用户名或密码不正确");
        }
        Object originalPassword = new String((char[]) token.getCredentials());
        SimpleHash encrypedPassword = new SimpleHash("MD5", originalPassword, null, 1);
//        if(!student.getPassword().equals(encrypedPassword.toString())){
//            throw new UnknownAccountException("用户名或密码不正确");
//        }
        return new SimpleAuthenticationInfo(student, originalPassword, "");
    }
}
