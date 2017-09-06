package com.jusfoun.hookah.oauth2server.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 扩展登录校验值，增加手机号和邮箱登录
 *
 * @author:jsshao
 * @date: 2017-5-9
 */
public class UsernameAndPasswordToken extends UsernamePasswordToken {
    private String email;

    private String mobile;

    public UsernameAndPasswordToken() {
        super();
    }

    public UsernameAndPasswordToken(String username, String password, String mobile, String email) {
        super(username, password);
        this.mobile = mobile;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
