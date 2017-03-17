package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.OrderGoodsMapper;
import com.jusfoun.hookah.core.domain.OrderGoods;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.OrderGoodsService;

import javax.annotation.Resource;

/**
 * 订单中商品服务
 * @author:jsshao
 * @date: 2017-3-17
 */
public class OrderGoodsServiceImpl extends GenericServiceImpl<OrderGoods, String> implements OrderGoodsService {

    @Resource
    private OrderGoodsMapper ordergoodsMapper;


    @Resource
    public void setDao(OrderGoodsMapper ordergoodsMapper) {
        super.setDao(ordergoodsMapper);
    }
}
