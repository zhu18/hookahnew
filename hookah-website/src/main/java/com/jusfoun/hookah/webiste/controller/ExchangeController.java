package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.rpc.api.CategoryService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/3/6 上午10:52
 * @desc
 */
@Controller
public class ExchangeController {
    @Resource
    CategoryService categoryService;
    @Resource
    GoodsService goodsService;
    @Resource
    MgGoodsService mgGoodsService;

    @RequestMapping(value = "/exchange", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("categoryInfo", categoryService.getCatTree());
        return "exchange/index";
    }

    @RequestMapping(value = "/exchange/list", method = RequestMethod.GET)
    public String list(Model model){
        return "exchange/list";
    }

    /**
     * 商品查询
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/exchange/details", method = RequestMethod.GET)
    public String details(@RequestParam(required = true) String id, Model model){
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goodsService.selectById(id), goodsVo);
        if(goodsVo != null) {
            MgGoods mgGoods = mgGoodsService.selectById(id);
            if (mgGoods != null) {
                goodsVo.setFormatList(mgGoods.getFormatList());
                goodsVo.setImgList(mgGoods.getImgList());
                goodsVo.setAttrTypeList(mgGoods.getAttrTypeList());
            }
        }
        model.addAttribute("exchange/details", goodsVo);
        return "exchange/details";
    }

    @RequestMapping(value = "/exchange/search", method = RequestMethod.GET)
    public String search(Model model){
        return "exchange/search";
    }
}
