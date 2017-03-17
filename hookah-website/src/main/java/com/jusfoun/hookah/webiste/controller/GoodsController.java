package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
