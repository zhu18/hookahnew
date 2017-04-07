package com.jusfoun.hookah.console.server.controller;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgShelvesGoods;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MgGoodsShelvesGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ctp on 2017/4/5.
 * 货架下的商品管理
 */
@Controller
@RequestMapping("/mgGoodssg")
public class MgGoodsShelvesGoodsController {

    @Autowired
    private MgGoodsShelvesGoodsService mgGoodsShelvesGoodsService;



    @RequestMapping("/addGSMongo")
    @ResponseBody
    public ReturnData addGoodsShelvesGoodsMongo(MgShelvesGoods mgShelvesGoods){
        return mgGoodsShelvesGoodsService.addMgGoodsSG(mgShelvesGoods);
    }


    @RequestMapping("/saveGSMongo")
    @ResponseBody
    public ReturnData saveGoodsShelvesGoodsMongo(MgShelvesGoods mgShelvesGoods){
        return  mgGoodsShelvesGoodsService.updateMgGoodsSG(mgShelvesGoods);
    }


    @RequestMapping("/findByIdGSMongo")
    @ResponseBody
    public ReturnData findByIdGSMongo(String shelvesGoodsId){
        return mgGoodsShelvesGoodsService.findByIdGSMongo(shelvesGoodsId);
    }

    @RequestMapping("/delGSMongo")
    @ResponseBody
    public ReturnData delGSMongo(String shelvesGoodsId){
        return mgGoodsShelvesGoodsService.delGSMongo(shelvesGoodsId);
    }


}
