package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.GoodsCheck;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GoodsCheckMapper extends GenericDao<GoodsCheck> {

    List<GoodsCheck> selectGoodsCheckVo(@Param("map") Map map);

}