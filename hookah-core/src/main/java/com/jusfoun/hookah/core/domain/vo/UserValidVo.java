package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.User;

public class UserValidVo extends User {
    //短信验证码
    private String validSms;

    //图片验证码
    private String captcha;


    private String passwordRepeat;

    public String getValidSms() {
        return validSms;
    }

    public void setValidSms(String validSms) {
        this.validSms = validSms;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}