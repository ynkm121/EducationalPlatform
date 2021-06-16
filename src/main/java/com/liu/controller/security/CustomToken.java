package com.liu.controller.security;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

public class CustomToken extends UsernamePasswordToken implements Serializable {
    private static final long serialVersionUID = 2020457391511655213L;

    private String loginType;

    public CustomToken() {}

    public CustomToken(final String username, final String password,
                            final String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
