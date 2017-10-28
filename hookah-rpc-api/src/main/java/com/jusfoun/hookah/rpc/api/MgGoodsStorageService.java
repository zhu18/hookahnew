package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.mongo.MgGoodsStorage;
import com.jusfoun.hookah.core.domain.vo.MgGoodsStorageVo;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * Created by wangjl on 2017-3-20.
 */
public interface MgGoodsStorageService extends GenericService<MgGoodsStorage,String> {
    void upsertDetails(MgGoodsStorage mgGoodsStorage);

    MgGoodsStorageVo findDetail(String storageId);
}
