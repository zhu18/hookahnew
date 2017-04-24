package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodsMapper extends GenericDao<Goods> {
    List<EsGoods> getNeedEsGoods();

    // 更新商品下架,商品下架清空商品上架时间
    @Update("update goods set is_onsale = 0, onsale_end_date = SYSDATE(), onsale_start_date = null, " +
            "check_status = 0 where goods_id = #{id}")
    int updateOffSale(String id);
}