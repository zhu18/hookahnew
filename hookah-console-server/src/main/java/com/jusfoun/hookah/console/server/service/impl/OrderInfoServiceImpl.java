package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.OrderInfoMapper;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.OrderInfoService;

import javax.annotation.Resource;

/**
 * 订单基本信息
 * @author:jsshao
 * @date: 2017-3-17
 */
public class OrderInfoServiceImpl extends GenericServiceImpl<OrderInfo, String> implements OrderInfoService {

    @Resource
    private OrderInfoMapper orderinfoMapper;


    @Resource
    public void setDao(OrderInfoMapper orderinfoMapper) {
        super.setDao(orderinfoMapper);
    }
}
