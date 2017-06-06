package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Comment;

/**
 * Created by ctp on 2017/6/6.
 */
public class CommentVo extends Comment {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
