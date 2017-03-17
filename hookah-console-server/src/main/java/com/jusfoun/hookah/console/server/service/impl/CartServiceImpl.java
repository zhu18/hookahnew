package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.CartMapper;
import com.jusfoun.hookah.core.domain.Cart;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.CartService;

import javax.annotation.Resource;

/**
 * 购物车服务
 * @author:jsshao
 * @date: 2017-3-17
 */
public class CartServiceImpl extends GenericServiceImpl<Cart, String> implements CartService {

    @Resource
    private CartMapper cartMapper;


    @Resource
    public void setDao(CartMapper cartMapper) {
        super.setDao(cartMapper);
    }
}
