package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.mongo.MgShelvesGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsShelvesVo;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsShelvesService;
import com.jusfoun.hookah.rpc.api.MgGoodsShelvesGoodsService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private GoodsShelvesService goodsShelvesService;


    /**
     * 获取货架下的商品集合
     * @param shelvesGoodsId
     * @return
     */
    @RequestMapping("/findByIdGSMongo")
    @ResponseBody
    public ReturnData findByIdGSMongo(String shelvesGoodsId){
        return mgGoodsShelvesGoodsService.findByIdGSMongo(shelvesGoodsId);
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


    @RequestMapping("/findAllShelf")
    @ResponseBody
    public ReturnData findAllShelves(){
        return goodsShelvesService.selectAllShelf();
    }


    @RequestMapping("/shelveGoodsList")
    @ResponseBody
    public Map<String, GoodsShelvesVo> shelveGoodsList(){
        Map<String,Object> params = new HashMap<String,Object>();
        return goodsShelvesService.getShevlesGoodsVoList(params);
    }
}
