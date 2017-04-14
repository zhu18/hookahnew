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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ctp on 2017/4/5.
 * 货架下的商品管理
 * 未做:1.同时添加多个
 *     2.同时删除多个
 *     3.add,update时返回一个
 */
@Controller
@RequestMapping("/mgGoodssg")
public class MgGoodsShelvesGoodsController {

    @Autowired
    private MgGoodsShelvesGoodsService mgGoodsShelvesGoodsService;


    /**
     * 添加货架下的商品
     * @param mgShelvesGoods
     * @return
     */
    @RequestMapping("/addGSMongo")
    @ResponseBody
    public ReturnData addGoodsShelvesGoodsMongo(MgShelvesGoods mgShelvesGoods){
        return mgGoodsShelvesGoodsService.addMgGoodsSG(mgShelvesGoods);
    }

    /**
     * 修改货架下的商品
     * @param mgShelvesGoods
     * @return
     */
    @RequestMapping("/updateGSMongo")
    @ResponseBody
    public ReturnData updateGoodsShelvesGoodsMongo(MgShelvesGoods mgShelvesGoods){
        return  mgGoodsShelvesGoodsService.updateMgGoodsSG(mgShelvesGoods);
    }


    /**
     * 获取货架下的商品集合
     * @param shelvesGoodsId
     * @return
     */
    @RequestMapping("/findByIdGSMongo")
    @ResponseBody
    public ReturnData findByIdGSMongo(@RequestParam(required = true) String shelvesGoodsId){
        return mgGoodsShelvesGoodsService.findByIdGSMongo(shelvesGoodsId);
    }

    /**
     * 删除货架索引
     * @param shelvesGoodsId
     * @return
     */
    @RequestMapping("/delGSMongo")
    @ResponseBody
    public ReturnData delGSMongo(@RequestParam(required = true) String shelvesGoodsId){
        return mgGoodsShelvesGoodsService.delGSMongo(shelvesGoodsId);
    }


    /**
     * 统计货架下的商品数量
     * @param shelvesGoodsId
     * @return
     */
    @RequestMapping("/countGoods")
    @ResponseBody
    public ReturnData countGoods(String shelvesGoodsId){
        return mgGoodsShelvesGoodsService.countShelvesGoods(shelvesGoodsId);
    }

}

