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

    //验证码
    private String picValid;

    //登录方式 用户名密码登录、第三方登录
    private TokenType tokenType;
    //是否需要验证码 true:需要
    private Boolean isValid = Boolean.FALSE;
    //是否锁定用户
    private Boolean locked = Boolean.FALSE;

    public UsernameAndPasswordToken() {
        super();
    }

    public UsernameAndPasswordToken(String username, String password, String mobile, String email) {
        super(username, password);
        this.mobile = mobile;
        this.email = email;
    }
    //用户名密码登录 第三方登录
    public enum TokenType {
        USERNAME_PASSWOR, CLIENT
    }

    public String getPicValid() {
        return picValid;
    }

    public void setPicValid(String picValid) {
        this.picValid = picValid;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
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

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}
