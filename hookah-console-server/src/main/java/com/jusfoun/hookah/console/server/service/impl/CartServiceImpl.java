package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.CartMapper;
import com.jusfoun.hookah.core.domain.Cart;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.CartService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务
 *
 * @author:jsshao
 * @date: 2017-3-17
 */
public class CartServiceImpl extends GenericServiceImpl<Cart, String> implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private GoodsService goodsService;


    @Resource
    public void setDao(CartMapper cartMapper) {
        super.setDao(cartMapper);
    }

    @Override
    public List<Cart> selectByIds(String[] ids) {
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.in("recId", ids));
            List<Cart> list = cartMapper.selectByExample(this.convertFilter2Example(filters));
            return list;
        } catch (Exception e) {
            logger.info("Exception:{}", e.getMessage());
            return null;
        }

    }


    @Override
    public void deleteByLogic(String id) {
        Cart cart = new Cart();
        cart.setRecId(id);
        cart.setIsDeleted(new Byte("1"));
        super.updateById(cart);
    }

    @Override
    public void deleteBatchByLogic(String[] ids) {
        Cart cart = new Cart();
        cart.setIsDeleted(new Byte("1"));
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.in("recId", ids));
        super.updateByCondition(cart, filters);
    }

    @Override
    public List<CartVo> selectDetailList(List<Condition> filters){
        List<Cart> carts = super.selectList(filters);
        List<CartVo> cartVos = new ArrayList<>(carts.size());
        Goods goods = null;
        try{
            for(Cart cart:carts){
                CartVo vo = new CartVo();
                BeanUtils.copyProperties(cart,vo);
                goods = goodsService.selectById(cart.getGoodsId());
                MgGoods.FormatBean format = goodsService.getFormat(cart.getGoodsId(),cart.getFormatId());
                vo.setGoods(goods);
                vo.setFormat(format);
                cartVos.add(vo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return cartVos;
    }
}
