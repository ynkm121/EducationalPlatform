package com.liu.controller.security.Realms;

import com.liu.entity.Admin;
import com.liu.entity.Teacher;
import com.liu.service.AdminService;
import com.liu.service.TeacherService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminRealm extends AuthorizingRealm {
    @Autowired
    AdminService adminService;

    public String getName(){
        return "AdminRealm";
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("管理员认证");
        Integer adminId = Integer.parseInt((String)authenticationToken.getPrincipal());
        Admin admin = adminService.getAdminById(adminId);
        if(admin == null){
            throw new UnknownAccountException("用户名或密码不正确");
        }

        return new SimpleAuthenticationInfo(admin.getAdminId(), admin.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("Admin");
        return info;
    }
}
