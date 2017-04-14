package com.jusfoun.hookah.rpc.api;

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
     *
     * @param goodsShelves
     * @return
     */
    GoodsShelves addGoodsShelves(GoodsShelves goodsShelves);

    /**
     * @return
     */
    ReturnData<List<Map<String,GoodsShelves>>> selectAllShelf();

    /**
     *
     * @param params
     * @return
     */
    Map<String,GoodsShelvesVo> getShevlesGoodsVoList(Map<String,Object> params);

    /**
     *
     * @param shevlesGoodsVoId
     * @return
     */
    GoodsShelvesVo findByShevlesGoodsVoId(String shevlesGoodsVoId);
}
