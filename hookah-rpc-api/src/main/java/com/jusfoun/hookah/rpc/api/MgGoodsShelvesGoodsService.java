package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.mongo.MgShelvesGoods;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.List;

/**
 * Created by ctp on 2017/4/5.
 */
public interface MgGoodsShelvesGoodsService extends GenericService<MgShelvesGoods,String> {

    ReturnData<List<MgShelvesGoods>> addMgGoodsSG(MgShelvesGoods mgShelvesGoods);

    ReturnData<List<MgShelvesGoods>> updateMgGoodsSG(MgShelvesGoods mgShelvesGoods);

    ReturnData<MgShelvesGoods> findByIdGSMongo(String shelvesGoodsId);

    ReturnData<MgShelvesGoods> delGSMongo(String shelvesGoodsId);
}