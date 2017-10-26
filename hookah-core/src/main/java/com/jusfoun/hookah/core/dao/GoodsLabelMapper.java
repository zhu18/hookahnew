package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.GoodsLabel;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsLabelMapper extends GenericDao<GoodsLabel> {
    @Select("select * from goods_label order by lab_py")
    List<GoodsLabel> getAllGoodsLabel();
}