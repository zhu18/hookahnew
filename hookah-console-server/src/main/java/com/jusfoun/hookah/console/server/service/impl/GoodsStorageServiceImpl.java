package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.GoodsStorageMapper;
import com.jusfoun.hookah.core.domain.GoodsStorage;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.GoodsStorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 标签管理
 * Created by wangjl on 2017-10-24.
 */
@Service
public class GoodsStorageServiceImpl extends GenericServiceImpl<GoodsStorage, String> implements GoodsStorageService {

    @Resource
    private GoodsStorageMapper goodsStorageMapper;

    @Resource
    public void setDao(GoodsStorageMapper goodsStorageMapper) {
        super.setDao(goodsStorageMapper);
    }

    public int insertSelective(GoodsStorage goodsStorage) {
        return goodsStorageMapper.insertSelective(goodsStorage);
    }
}
