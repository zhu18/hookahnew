package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsShelvesVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.rpc.api.CategoryService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.GoodsShelvesService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huang lei
 * @date 2017/3/6 上午10:52
 * @desc
 */
@Controller
@RequestMapping("/exchange")
public class ExchangeController {
    @Resource
    CategoryService categoryService;
    @Resource
    GoodsService goodsService;
    @Resource
    MgGoodsService mgGoodsService;
    @Resource
    GoodsShelvesService goodsShelvesService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("categoryInfo", categoryService.getCatTree());
        model.addAttribute("goodsShelvesVoInfo",goodsShelvesService.getShevlesGoodsVoList(new HashMap<String,Object>()));
        return "exchange/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "exchange/list";
    }

    /**
     * 商品查询
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String details(@RequestParam(required = true) String id, Model model) {
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goodsService.selectById(id), goodsVo);
        if (goodsVo != null) {
            MgGoods mgGoods = mgGoodsService.selectById(id);
            if (mgGoods != null) {
                goodsVo.setFormatList(mgGoods.getFormatList());
                goodsVo.setImgList(mgGoods.getImgList());
                goodsVo.setAttrTypeList(mgGoods.getAttrTypeList());
                goodsVo.setApiInfo(mgGoods.getApiInfo());
            }
        }
        model.addAttribute("goodsDetails", goodsVo);

        //推荐商品
        Map<String,GoodsShelvesVo> goodsMap = goodsShelvesService.getShevlesGoodsVoList(new HashMap<String,Object>());
        model.addAttribute("reCommData", goodsMap.get("recomm_data"));
        return "exchange/details";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model) {
        return "exchange/search";
    }

    @RequestMapping(value = "/addToCart", method = RequestMethod.GET)
    public String addToCart(String goodsId, Model model) {
        Goods goods = goodsService.selectById(goodsId);
        model.addAttribute("goodsInfo", goods);
        return "exchange/addToCart";
    }

    @RequestMapping(value = "/shelves", method = RequestMethod.GET)
    public String shelves(Model model) {
        return "exchange/shelves";
    }
}
