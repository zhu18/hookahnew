package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.OrderActionMapper;
import com.jusfoun.hookah.core.domain.OrderAction;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.OrderActionService;

import javax.annotation.Resource;

/**
 * 订单操作服务
 * @author:jsshao
 * @date: 2017-3-17
 */
public class OrderActionServiceImpl extends GenericServiceImpl<OrderAction, String> implements OrderActionService {

    @Resource
    private OrderActionMapper orderactionMapper;


    @Resource
    public void setDao(OrderActionMapper orderactionMapper) {
        super.setDao(orderactionMapper);
    }
}
