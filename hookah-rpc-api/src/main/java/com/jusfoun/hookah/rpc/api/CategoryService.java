package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.CategoryVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * Created by wangjl on 2017-3-15.
 */
public interface CategoryService extends GenericService<Category,String> {
    public List<CategoryVo> getCatTree();
}
