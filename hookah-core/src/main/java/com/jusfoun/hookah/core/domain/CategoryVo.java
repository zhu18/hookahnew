package com.jusfoun.hookah.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjl on 2017-3-22.
 */
public class CategoryVo extends Category {
    private List<Category> children = new ArrayList();

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}
