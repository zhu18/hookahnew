package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.GoodsStorage;
import com.jusfoun.hookah.core.domain.vo.GoodsLabelsPagVo;
import com.jusfoun.hookah.core.domain.vo.GoodsStorageVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * 新货架
 * Created by wangjl on 2017-10-28.
 */
public interface GoodsStorageService extends GenericService<GoodsStorage,String> {
    int insertSelective(GoodsStorage goodsStorage);

    List<GoodsStorageVo> getGoodsStorageList();

    GoodsLabelsPagVo searchByLabels(String storageId, int typeId, String labels, Integer currentPage, Integer pageSize) throws Exception;
}