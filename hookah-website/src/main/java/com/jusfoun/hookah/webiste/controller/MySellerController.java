package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.rpc.api.CategoryService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgCategoryAttrTypeService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
    @Resource
    MgGoodsService mgGoodsService;

    @RequestMapping(value = "/myseller", method = RequestMethod.GET)
    public String index(Model model){
        // 默认加载分类
        try{
            model.addAttribute("categoryList", categoryService.getCatTree());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "/myseller/index";
    }

    @RequestMapping(value = "/myseller/publish", method = RequestMethod.GET)
    public String publish(String id, Model model){
        if(StringUtils.isNotBlank(id)) {
            // 查询商品信息
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("goodsId", id));
            filters.add(Condition.eq("domainId", "123"));
            filters.add(Condition.eq("isOnsale", "1"));
            Goods goods = goodsService.selectOne(filters);
            if (goods != null && StringUtils.isNotBlank(goods.getGoodsId())) {
                GoodsVo goodsVo = new GoodsVo();
                BeanUtils.copyProperties(goods, goodsVo);
                MgGoods mgGoods = mgGoodsService.selectById(id);
                if (mgGoods != null) {
                    goodsVo.setFormatList(mgGoods.getFormatList());
                    goodsVo.setImgList(mgGoods.getImgList());
                    goodsVo.setAttrTypeList(mgGoods.getAttrTypeList());
                    model.addAttribute("goodsInfo", goodsVo);
                }
            }
        }
        return "/myseller/publish";
    }

    @RequestMapping(value = "/myseller/trade", method = RequestMethod.GET)
    public String trade(){
        return "/myseller/trade";
    }

    @RequestMapping(value = "/myseller/tradeing", method = RequestMethod.GET)
    public String tradeing(String pageNumber, String pageSize, String goodsName, Byte checkStatus, Model model){
        Pagination<Goods> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if(StringUtils.isNotBlank(pageNumber)){
                pageNumberNew = Integer.parseInt(pageNumber);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if(StringUtils.isNotBlank(pageSize)){
                pageSizeNew = Integer.parseInt(pageSize);
            }
            //只查询商品状态为未删除的商品
            filters.add(Condition.eq("isDelete", 1));

            if( StringUtils.isNotBlank(goodsName)){
                filters.add(Condition.like("goodsName", goodsName.trim()));
            }
            if(checkStatus != null){
                filters.add(Condition.like("checkStatus", checkStatus));
            }
            page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            model.addAttribute("pageInfo", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/myseller/tradeing";
    }

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
