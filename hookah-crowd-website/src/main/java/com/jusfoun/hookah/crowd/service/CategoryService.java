package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.CategoryVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * Created by wangjl on 2017-3-15.
 */
public interface CategoryService extends GenericService<Category,String> {

    /**
     * 前台专用
     * @return
     */
    public List<CategoryVo> getCatTree();

}
