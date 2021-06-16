package com.liu.controller.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        //做Realm的一个校验
        assertRealmsConfigured();
        //获取前端传递过来的token
        CustomToken customToken=(CustomToken)authenticationToken;
        //现在就可以获取这个登陆的类型了
        String loginType = customToken.getLoginType();  //  登陆类型   1：User   Admin
        //获取所有的realms()
        Collection<Realm> realms = getRealms();
        //登陆类型对应的所有realm全部获取到
        Collection<Realm> typeRealms = new ArrayList<>();
        for (Realm realm:realms){
            //realm类型和现在登陆的类型做一个对比
            if(realm.getName().contains(loginType)){   //就能分开这两个realm
                typeRealms.add(realm);
            }
        }

        if(typeRealms.size() == 1){
            return doSingleRealmAuthentication(typeRealms.iterator().next(),customToken);
        }else{
            return doMultiRealmAuthentication(typeRealms,customToken);
        }
    }
}
