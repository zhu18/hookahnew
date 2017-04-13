package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.SysMessage;
import com.jusfoun.hookah.core.domain.User;

import java.util.Date;

/**
 * @author:jsshao
 * @date: 2017-4-11
 */
public class SysMessageVo extends SysMessage{
    private User user;

    private Date readTime;

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
