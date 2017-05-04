package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface GoodsMapper extends GenericDao<Goods> {
    List<EsGoods> getNeedEsGoods();

    // 更新商品下架状态和下架时间
    @Update("update goods set is_onsale = 0, onsale_end_date = SYSDATE() where goods_id = #{id}")
    int updateOffSale(String id);

    EsGoods getNeedEsGoodsById(String id);

    EsGoods getNeedGoodsById(String id);

    List<Goods> waitList(GoodsVo vo);

    Integer waitListCnt(GoodsVo vo);

    int updateByGidForFollowNum(Map<String, Object> map);

}