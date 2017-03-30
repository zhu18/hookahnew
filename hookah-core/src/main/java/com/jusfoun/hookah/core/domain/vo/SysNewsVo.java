package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.SysNews;

/**
 * 文章发布者信息
 */
public class SysNewsVo extends SysNews {
    private String userName;

    private String headImg;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
