package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.CategoryMapper;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 分类查询
 * Created by wangjl on 2017-3-15.
 */
@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, String> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;


    @Resource
    public void setDao(CategoryMapper categoryMapper) {
        super.setDao(categoryMapper);
    }
}
