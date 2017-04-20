package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper extends GenericDao<Category> {

    public String findMaxByParentId(@Param("parentId") String parentId);

}