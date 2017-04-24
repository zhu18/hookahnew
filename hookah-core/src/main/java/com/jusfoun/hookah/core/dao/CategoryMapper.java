package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.es.EsCategory;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends GenericDao<Category> {

    String findMaxByParentId(@Param("parentId") String parentId);

    List<EsCategory> getNeedEsCat();

}