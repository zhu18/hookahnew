package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.List;

public interface GoodsMapper extends GenericDao<Goods> {
    List<EsGoods> getNeedEsGoods();
}