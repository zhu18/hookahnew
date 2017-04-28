package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Cart;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CartService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/3/7 下午6:08
 * @desc 买家中心
 */
@Controller
public class CartController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "usercenter/buyer/cart", method = RequestMethod.GET)
    public String cart(Model model) {
        try {
            /*String userId = this.getCurrentUser().getUserId();
            logger.info("当前用户是:{}", userId);

            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("userId", userId));
            filters.add(Condition.eq("isDeleted", (byte)0));

            List<CartVo> cartVos = cartService.selectDetailList(filters);
            logger.info(JSONUtils.toString(cartVos));*/
            //model.addAttribute("cartList", cartVos);
            return "usercenter/buyer/cart";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return "redirect:/error/500";
        }
    }

    /**
     * Ajax 方式增加购物车
     *
     * @param cart
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cart/add", method = RequestMethod.POST)
    public ReturnData add(Cart cart, Model model) {
        try {
            //需要先获取当前用户id
            String userId = this.getCurrentUser().getUserId();
            logger.info("当前用户是:{}", userId);

            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("userId", userId));
            filters.add(Condition.eq("goodsId", cart.getGoodsId()));
            filters.add(Condition.eq("formatId", cart.getFormatId()));
            filters.add(Condition.eq("isDeleted", 0));
            Cart existCart  = cartService.selectOne(filters);
            if(existCart!=null){
                MgGoods.FormatBean format= goodsService.getFormat(cart.getGoodsId(),cart.getFormatId());

                //补充商品信息
                existCart.setGoodsNumber(existCart.getGoodsNumber()+cart.getGoodsNumber());
                //入库

                cartService.updateByIdSelective(existCart);
            }else{
                cart.setUserId(userId);
                cart.setAddTime(new Date());
                cart.setIsGift(new Integer(0).shortValue());
                cart.setIsDeleted(new Byte("0"));

                MgGoods.FormatBean format= goodsService.getFormat(cart.getGoodsId(),cart.getFormatId());

                //补充商品信息
                Goods goods = goodsService.selectById(cart.getGoodsId());
                cart.setGoodsSn(goods.getGoodsSn());
                cart.setGoodsName(goods.getGoodsName());
                cart.setGoodsImg(goods.getGoodsImg());
                cart.setGoodsFormat(format.getFormat());
                cart.setFormatNumber((long)format.getNumber());
                cart.setGoodsPrice(format.getPrice());
                //入库

                cartService.insert(cart);
            }



            return ReturnData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
    }

    /**
     * Ajax 方式增加购物车
     *
     * @param list
     * @param model
     * @return
     */
    /*@ResponseBody
    @RequestMapping(value = "/cart/addAll", method = RequestMethod.POST)
    public ReturnData addAll(List<Cart> list, Model model) {
        try {
            //需要先获取当前用户id
            String userId = "hookah";
            for (Cart cart : list) {
                cart.setUserId(userId);
                cart.setAddTime(new Date());
                cart.setIsGift(new Integer(0).shortValue());

                Goods goods = goodsService.selectById(cart.getGoodsId());
                cart.setGoodsSn(goods.getGoodsSn());
                cart.setGoodsName(goods.getGoodsName());
                cart.setGoodsImg(goods.getGoodsImg());
            }

            cartService.insertBatch(list);
            return ReturnData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
    }*/

    /**
     * Ajax  编辑购物车
     *
     * @param cart
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cart/edit", method = RequestMethod.POST)
    public ReturnData edit(Cart cart, Model model) {
        if (StringUtils.isBlank(cart.getRecId())) {
            return ReturnData.invalidParameters("The field[recId] CANNOT be null!");
        }
        try {
            cartService.updateByIdSelective(cart);
            return ReturnData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
    }

    /**
     * Ajax 方式增加购物车
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cart/delete/{id}", method = RequestMethod.GET)
    public ReturnData delete(@PathVariable String id) {
        logger.info("逻辑删除购物车：{}", id);
        try {
            cartService.deleteByLogic(id);
            return ReturnData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
    }

    /**
     * Ajax 方式增加购物车
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cart/deleteAll", method = RequestMethod.POST)
    public ReturnData deleteAll(@RequestBody String[] ids) {
        logger.info("逻辑删除购物车：{}", ids);
        try {
            cartService.deleteBatchByLogic(ids);
            return ReturnData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
    }
}
