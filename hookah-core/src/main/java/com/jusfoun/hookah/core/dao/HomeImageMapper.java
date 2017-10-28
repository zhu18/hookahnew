package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.HomeImage;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

public interface HomeImageMapper extends GenericDao<HomeImage> {

    Byte findMaxSortVal();

    int updateSortValByImgId(@Param("imgId") String imgId);
}