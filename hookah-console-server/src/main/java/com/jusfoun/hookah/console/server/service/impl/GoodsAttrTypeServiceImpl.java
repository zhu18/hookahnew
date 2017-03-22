package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.GoodsAttrTypeMapper;
import com.jusfoun.hookah.core.domain.GoodsAttrType;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.GoodsAttrTypeService;

import javax.annotation.Resource;

/**
 * Created by wangjl on 2017-3-16.
 */
public class GoodsAttrTypeServiceImpl extends GenericServiceImpl<GoodsAttrType, String> implements GoodsAttrTypeService {
    @Resource
    private GoodsAttrTypeMapper goodsAttrTypeMapper;


    @Resource
    public void setDao(GoodsAttrTypeMapper goodsAttrTypeMapper) {
        super.setDao(goodsAttrTypeMapper);
    }
}
