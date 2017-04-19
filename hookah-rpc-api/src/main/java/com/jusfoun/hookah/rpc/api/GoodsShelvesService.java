package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsShelves;
import com.jusfoun.hookah.core.domain.vo.GoodsShelvesVo;
import com.jusfoun.hookah.core.domain.vo.OptionalShelves;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface GoodsShelvesService extends GenericService<GoodsShelves,String> {

    /**
     *添加货架
     * @param goodsShelves
     * @return
     */
    GoodsShelves addGoodsShelves(GoodsShelves goodsShelves);

    /**
     * 查询所有货架
     * @return
     */
    ReturnData<List<Map<String,GoodsShelves>>> selectAllShelf();

    /**
     *获取所有货架 VO(包括货架内的商品信息)
     * @param params
     * @return
     */
    Map<String,GoodsShelvesVo> getShevlesGoodsVoList(Map<String,Object> params);

    /**
     *查询单个货架信息 VO(包括货架内的商品信息)
     * @param shevlesGoodsVoId
     * @return
     */
    ReturnData<GoodsShelvesVo> findByShevlesGoodsVoId(String shevlesGoodsVoId);

    /**
     * 根据货架Id查询关联的所有商品
     * @param shevlesGoodsVoId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    ReturnData findGoodsByShevlesId(String shevlesGoodsVoId, String pageNumber, String pageSize);
}
