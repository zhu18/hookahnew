package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsFavorite;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsShelvesVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class ExchangeController extends BaseController{
    @Resource
    CategoryService categoryService;
    @Resource
    GoodsService goodsService;
    @Resource
    MgGoodsService mgGoodsService;
    @Resource
    GoodsShelvesService goodsShelvesService;

    @Resource
    CommentService commentService;

    @Resource
    GoodsFavoriteService goodsFavoriteService;

    @Resource
    MgOrderInfoService mgOrderInfoService;

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
    public String details(@RequestParam String id, Model model) {
        try {
            mgGoodsService.updateClickRate(id);//增加商品点击量记录
            // 查询商品详情
            GoodsVo goodsVo = goodsService.findGoodsById(id);
            if(goodsVo == null) {
                new HookahException("未找到商品！goodsVo == null");
            }
            if(goodsVo.getClickRate() == null) {
                goodsVo.setClickRate((long)0);
            }
            // 获取永和关注信息
            try {
                if(StringUtils.isNotBlank(getCurrentUser().getUserId())){
                    List<Condition> filters = new ArrayList();
                    filters.add(Condition.eq("goodsId", id));
                    filters.add(Condition.eq("userId", getCurrentUser().getUserId()));
                    GoodsFavorite goodsFavorite = goodsFavoriteService.selectOne(filters);
                    if(goodsFavorite != null){
                        goodsVo.setOrNotFavorite(true);
                    }else{
                        goodsVo.setOrNotFavorite(false);
                    }
                }
            } catch (HookahException e) {
                if("没有登录用户信息".equals(e.getMessage())){
                    goodsVo.setOrNotFavorite(false);
                }
            }

            model.addAttribute("goodsGrades",commentService.countGoodsGradesByGoodsId(id).getData());

            model.addAttribute("goodsDetails", goodsVo);
            //推荐商品
            Map<String,GoodsShelvesVo> goodsMap = goodsShelvesService.getShevlesGoodsVoList(new HashMap<String,Object>());
            model.addAttribute("reCommData", goodsMap.get("recomm_data"));

            return "exchange/details";
        }catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return "/error/500";
        }
    }
    @RequestMapping(value = "/orderEndDetails", method = RequestMethod.GET)
    public String orderEndDetails(@RequestParam String orderSn,@RequestParam String id, Model model) throws HookahException {
        mgGoodsService.updateClickRate(id);//增加商品点击量记录
        // 查询商品详情
        GoodsVo goodsVo = goodsService.findGoodsByIdWebsite(id);
        // 获取永和关注信息
        try {
            if(StringUtils.isNotBlank(getCurrentUser().getUserId())){
                List<Condition> filters = new ArrayList();
                filters.add(Condition.eq("goodsId", id));
                filters.add(Condition.eq("userId", getCurrentUser().getUserId()));
                GoodsFavorite goodsFavorite = goodsFavoriteService.selectOne(filters);
                if(goodsFavorite != null){
                    goodsVo.setOrNotFavorite(true);
                }else{
                    goodsVo.setOrNotFavorite(false);
                }
            }
        } catch (HookahException e) {
            if(!"没有登录用户信息".equals(e.getMessage())){
                throw new HookahException("获取用户信息出错！",e);
            }
        }

        List<Condition> filters = new ArrayList<>(1);
        filters.add(Condition.eq("orderSn",orderSn));
        OrderInfoVo orderInfoVo = mgOrderInfoService.selectOne(filters);
        MgOrderGoods orderGoods = orderInfoVo.getMgOrderGoodsList().stream()
                                        .filter(g->g.getGoodsId().equals(id))
                                        .findFirst().get();

        model.addAttribute("orderGoods",orderGoods);
        model.addAttribute("goodsGrades",commentService.countGoodsGradesByGoodsId(id).getData());

        model.addAttribute("goodsDetails", goodsVo);
        //推荐商品
        Map<String,GoodsShelvesVo> goodsMap = goodsShelvesService.getShevlesGoodsVoList(new HashMap<String,Object>());
        model.addAttribute("reCommData", goodsMap.get("recomm_data"));
        return "exchange/orderEndDetails";
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
