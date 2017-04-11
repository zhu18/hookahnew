package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Category;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-4-10.
 */
public class EsCategoryVo extends Category implements Serializable {
    private Long cnt;

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }
}
