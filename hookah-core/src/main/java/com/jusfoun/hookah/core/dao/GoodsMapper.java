package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodsMapper extends GenericDao<Goods> {
    List<EsGoods> getNeedEsGoods();

    // 更新商品下架状态和下架时间
    @Update("update goods set is_onsale = 0, onsale_end_date = SYSDATE() where goods_id = #{id}")
    int updateOffSale(String id);
}