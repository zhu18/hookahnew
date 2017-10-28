package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.GoodsStorage;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * 新货架
 * Created by wangjl on 2017-10-28.
 */
public interface GoodsStorageService extends GenericService<GoodsStorage,String> {
    int insertSelective(GoodsStorage goodsStorage);
}
