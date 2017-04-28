package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.SysNews;

/**
 * 文章发布者信息
 */
public class SysNewsVo extends SysNews {
    private String creatName;

    private String headImg;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getCreatName() {
        return creatName;
    }

    public void setCreatName(String creatName) {
        this.creatName = creatName;
    }
}
