package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class GoodsServiceImpl extends GenericServiceImpl<Goods, String> implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;


    @Resource
    public void setDao(GoodsMapper goodsMapper) {
        super.setDao(goodsMapper);
    }
}
