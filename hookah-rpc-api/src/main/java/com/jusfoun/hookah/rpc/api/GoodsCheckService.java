package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.GoodsCheck;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * Created by admin on 2017/4/7/0007.
 */
public interface GoodsCheckService extends GenericService<GoodsCheck, String> {

    void insertRecord(GoodsCheck goodsCheck) throws HookahException;

    GoodsCheck selectOneByGoodsId(String goodsId);
}
