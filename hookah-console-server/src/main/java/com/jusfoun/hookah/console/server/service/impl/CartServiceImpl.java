package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.CartMapper;
import com.jusfoun.hookah.core.domain.Cart;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.CartService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Cart> selectByIds(String[] ids) {
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.in("recId",ids));
            List<Cart> list = cartMapper.selectByExample(this.convertFilter2Example(filters));
            return list;
        }catch (Exception e){
            logger.info("Exception:{}",e.getMessage());
            return  null;
        }

    }

    @Override
    public void deleteByLogic(String id) {
        Cart cart = new Cart();
        cart.setRecId(id);
        cart.setDelFlag(new Integer(1).shortValue());
        super.updateById(cart);
    }

    @Override
    public void deleteBatchByLogic(String[] ids) {
        Cart cart = new Cart();
        cart.setDelFlag(new Integer(1).shortValue());

        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.in("recId",ids));
        super.updateByCondition(cart,filters);
    }
}
