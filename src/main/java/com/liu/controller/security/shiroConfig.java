package com.liu.controller.security;

import com.liu.controller.security.Realms.AdminRealm;
import com.liu.controller.security.Realms.StudentRealm;
import com.liu.controller.security.Realms.TeacherRealm;
import com.liu.controller.security.Realms.UserRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Configuration
public class shiroConfig {

    @Bean
    public ShiroFilterFactoryBean factoryBean(@Qualifier("DefaultSecurityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        /*
            anon:无拦截
            authc：必须认证才能访问
            user：必须勾选 记住我 才能访问
            perms：拥有某个资源权限才能访问
            role：拥有角色权限才能访问
         */
        bean.setLoginUrl("/login");
        bean.setUnauthorizedUrl("/error");
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        // 静态资源放行
        filterMap.put("*.js", "anon");
        filterMap.put("*.css", "anon");
        filterMap.put("/dist/**", "anon");
        filterMap.put("/plugins/**", "anon");
        filterMap.put("/css/**", "anon");
        // 权限认证
        filterMap.put("/login", "anon");
        filterMap.put("/student/**", "authc,perms[Student]");
        filterMap.put("/teacher/**", "authc,perms[Teacher]");
        filterMap.put("/admin/**", "authc,perms[Admin]");
        bean.setFilterChainDefinitionMap(filterMap);

        return bean;
    }

    @Bean(name = "DefaultSecurityManager")
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置校验器
        securityManager.setAuthenticator(authenticator());
        List<Realm> realms = new ArrayList<>();
        realms.add(studentRealm());
        realms.add(teacherRealm());
        realms.add(adminRealm());
        //设置自定义授权器
        CustomedModularRealmAuthorizer authorizer = new CustomedModularRealmAuthorizer();
        authorizer.setRealms(realms);
        securityManager.setAuthorizer(authorizer);
        //设置Realm
        securityManager.setRealms(realms);
        return securityManager;
    }


    @Bean(name = "StudentRealm")
    public StudentRealm studentRealm(){
        return new StudentRealm();
    }

    @Bean(name = "TeacherRealm")
    public TeacherRealm teacherRealm(){
        return new TeacherRealm();
    }

    @Bean(name = "AdminRealm")
    public AdminRealm adminRealm(){
        return new AdminRealm();
    }

    //下面就是认证器的配置
    @Bean
    public CustomModularRealmAuthenticator authenticator(){
        return new CustomModularRealmAuthenticator();
    }
}
