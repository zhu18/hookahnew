package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author bingbing wu
 * @date 2017/3/13 下午9:33
 * @desc
 */
@RestController
@RequestMapping("goods")
public class GoodsController {
    @Resource
    GoodsService goodsService;

    @RequestMapping("add")
    public ReturnData addGoods(Goods obj) {
        return null;
    }

    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    public String list(){
        return "/goods/list";
    }

    @RequestMapping(value = "/goods/details", method = RequestMethod.GET)
    public String details(){
        return "/goods/details";
    }



}
