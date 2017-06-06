package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.rpc.api.CategoryService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgCategoryAttrTypeService;
import com.jusfoun.hookah.rpc.api.MgGoodsService;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/usercenter")
public class MySellerController {
    @Resource
    CategoryService categoryService;
    @Resource
    MgCategoryAttrTypeService mgCategoryAttrTypeService;
    @Resource
    GoodsService goodsService;
    @Resource
    MgGoodsService mgGoodsService;

    @RequestMapping(value = "/goodsManage", method = RequestMethod.GET)
    public String goodsManage(String pageNumber, String pageSize, String goodsName, Byte checkStatus, Model model) {
        Pagination<Goods> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));
            //只查询商品状态为未删除的商品
            filters.add(Condition.eq("isDelete", 1));
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(pageNumber)) {
                pageNumberNew = Integer.parseInt(pageNumber);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }

            if (StringUtils.isNotBlank(goodsName)) {
                filters.add(Condition.like("goodsName", goodsName.trim()));
            }
            if (checkStatus != null) {
                filters.add(Condition.like("checkStatus", checkStatus));
            }
            page = goodsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            model.addAttribute("pageInfo", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "usercenter/myseller/goodsManage";
    }

    @RequestMapping(value = "/goodsPublish", method = RequestMethod.GET)
    public String goodsPublish(){
        return "usercenter/myseller/goodsPublish";
    }

    @RequestMapping(value = "/goodsEdit", method = RequestMethod.GET)
    public String publish() {
        return "usercenter/myseller/goodsEdit";
    }

    @RequestMapping(value = "/goodsModify", method = RequestMethod.GET)
    public String goodsModify(){
        return "usercenter/myseller/goodsModify";
    }

    @RequestMapping(value = "/trade", method = RequestMethod.GET)
    public String trade(){
        return "usercenter/myseller/trade";
    }

    @RequestMapping(value = "/tradeRate", method = RequestMethod.GET)
    public String tradeRate(){
        return "usercenter/myseller/tradeRate";
    }

    @RequestMapping(value = "/custom", method = RequestMethod.GET)
    public String custom(){
        return "usercenter/myseller/custom";
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public String customer(){
        return "usercenter/myseller/customer";
    }

    @RequestMapping(value = "/customega", method = RequestMethod.GET)
    public String customega(){
        return "usercenter/myseller/customega";
    }

    @RequestMapping(value = "/goodsOffSale", method = RequestMethod.GET)
    public String goodsOffSale(){
        return "usercenter/myseller/goodsOffSale";
    }

    @RequestMapping(value = "/goodsWait", method = RequestMethod.GET)
    public String goodsWait(){
        return "usercenter/myseller/goodsWait";
    }

    @RequestMapping(value = "/goodsNoFailed", method = RequestMethod.GET)
    public String goodsNoFailed(){
        return "usercenter/myseller/goodsNoFailed";
    }

    @RequestMapping(value = "/goodsIllegal", method = RequestMethod.GET)
    public String goodsIllegal(){
        return "usercenter/myseller/goodsIllegal";
    }



}
