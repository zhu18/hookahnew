package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * @author huang lei
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface GoodsService extends GenericService<Goods,String> {
    void addGoods(GoodsVo obj) throws HookahException;
    void updateGoods(GoodsVo obj) throws HookahException;

    MgGoods.FormatBean getFormat(String goodsId, Integer formatId) throws Exception;

    int updateGoodsStatus(String goodsId, String status);

    int onsale(String goodsId, String dateTime);

    Pagination saleList(String pageNum, String pageSize, String goodsName, String userId);

    Pagination waitList(String pageNum, String pageSize, String goodsName, String userId, Integer checkStatus, Integer isBook);

    Pagination offsaleList(String pageNum, String pageSize, String goodsName, String userId);

    Pagination illegalList(String pageNum, String pageSize, String goodsName, String userId);

    GoodsVo findGoodsById(String goodsId) throws HookahException;

    int updateByGidForFollowNum(Map<String, Object> map);

    GoodsVo findGoodsByIdWebsite(String goodsId) throws HookahException;
}
