package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.rpc.api.CategoryService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgCategoryAttrTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/3/7 下午6:00
 * @desc 卖家中心
 */
@Controller
public class MySellerController {
    @Resource
    CategoryService categoryService;
    @Resource
    MgCategoryAttrTypeService mgCategoryAttrTypeService;
    @Resource
    GoodsService goodsService;

    @RequestMapping(value = "/myseller", method = RequestMethod.GET)
    public String index(Model model){
        // 默认加载分类
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("catSign", 1));
        filters.add(Condition.eq("isShow", 1));
        List<Category> list = (List) categoryService.selectList(filters);
        model.addAttribute("categoryList", list);
        return "/myseller/index";
    }

    @RequestMapping(value = "/myseller/publish", method = RequestMethod.GET)
    public String publish(GoodsVo obj, Model model){
        return "/myseller/publish";
    }

    @RequestMapping(value = "/myseller/trade", method = RequestMethod.GET)
    public String trade(){
        return "/myseller/trade";
    }

    @RequestMapping(value = "/myseller/tradeing", method = RequestMethod.GET)
    public String tradeing(){ return "/myseller/tradeing"; }

    @RequestMapping(value = "/myseller/rate", method = RequestMethod.GET)
    public String mysellrate(){
        return "/myseller/rate";
    }

    @RequestMapping(value = "/myseller/custom", method = RequestMethod.GET)
    public String custom(){
        return "/myseller/custom";
    }

    @RequestMapping(value = "/myseller/customer", method = RequestMethod.GET)
    public String customer(){
        return "/myseller/customer";
    }

    @RequestMapping(value = "/myseller/illegal", method = RequestMethod.GET)
    public String illegal(){

        return "/myseller/illegal";
    }
}
