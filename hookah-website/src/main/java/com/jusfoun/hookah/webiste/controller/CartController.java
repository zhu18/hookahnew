package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Cart;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CartService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/3/7 下午6:08
 * @desc 买家中心
 */
@Controller
public class CartController {
    private final static Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart(Model model) {
        try {
            List<Condition> filters = new ArrayList<>();
            /*
            filters.add(Condition.eq("userId", "<当前用户id>"));
             */

            List<Cart> carts = cartService.selectList(filters);
            model.addAttribute("cartList", carts);
            return "/mybuyer/cart";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "redirect:/error/500";
        }
    }

    /**
     * Ajax 方式增加购物车
     * @param cart
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ReturnData add(@RequestBody Cart cart, Model model) {
        try {
            //需要先获取当前用户id
            String userId = "";
            cart.setUserId(userId);
            cart.setAddTime(new Date());
            cart.setGoodsNumber(new Integer(1).shortValue());
            cartService.insert(cart);
            return ReturnData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
    }

    /**
     * Ajax 方式增加购物车
     * @param list
     * @param model
     * @return
     */
    @RequestMapping(value = "/addAll", method = RequestMethod.GET)
    public ReturnData addAll(@RequestBody List<Cart> list, Model model) {
        try {
            //需要先获取当前用户id
            String userId = "";
            for(Cart cart:list){
                cart.setUserId(userId);
                cart.setAddTime(new Date());
                cart.setGoodsNumber(new Integer(1).shortValue());
            }

            cartService.insertBatch(list);
            return ReturnData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
    }

    /**
     * Ajax  编辑购物车
     * @param cart
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ReturnData edit(@RequestBody Cart cart, Model model) {
        if(StringUtils.isBlank(cart.getRecId())){
            return ReturnData.invalidParameters("The field[recId] CANNOT be null!");
        }
        try {
            cartService.insert(cart);
            return ReturnData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
    }

    /**
     * Ajax 方式增加购物车
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ReturnData delete(@RequestParam String id) {
        try {
            cartService.delete(id);
            return ReturnData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
    }

    /**
     * Ajax 方式增加购物车
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
    public ReturnData deleteAll(@RequestBody String[] ids) {
        try {
            List<Condition> filters = new ArrayList<>();
            cartService.delete(ids);
            return ReturnData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
    }
}
