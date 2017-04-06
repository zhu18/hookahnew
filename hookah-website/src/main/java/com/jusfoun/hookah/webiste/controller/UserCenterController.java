package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.rpc.api.*;
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
 * @date 2017/3/7 下午6:05
 * @desc
 */
@Controller
public class UserCenterController {

    @Resource
    SysNewsService sysNewsService;
    @Resource
    CategoryService categoryService;
    @Resource
    MgCategoryAttrTypeService mgCategoryAttrTypeService;
    @Resource
    GoodsService goodsService;
    @Resource
    MgGoodsService mgGoodsService;

    @RequestMapping(value = "/1/usercenter", method = RequestMethod.GET)
    public String index1(){
        return "1/usercenter/index";
    }

    @RequestMapping(value = "/1/usercenter/fundmanage", method = RequestMethod.GET)
    public String fundmanage() { return "1/usercenter/fundmanage"; }

    @RequestMapping(value = "/1/usercenter/safeset", method = RequestMethod.GET)
    public String safeset() { return "1/usercenter/safeset"; }

    @RequestMapping(value = "/1/usercenter/recharge", method = RequestMethod.GET)
    public String recharge() { return "1/usercenter/recharge"; }

    @RequestMapping(value = "/1/usercenter/withdrawals", method = RequestMethod.GET)
    public String withdrawals() { return "1/usercenter/withdrawals"; }



    @RequestMapping(value = "/1/usercenter/publishArticle", method = RequestMethod.GET)
    public String publishArticle() { return "1/usercenter/publishArticle"; }



    @RequestMapping(value = "/usercenter/userInfo", method = RequestMethod.GET)
    public String userInfo() { return "usercenter/userInfo"; }

    @RequestMapping(value = "/usercenter/safeSet", method = RequestMethod.GET)
    public String safeSet() { return "usercenter/safeSet"; }

    @RequestMapping(value = "/usercenter/infoCenter", method = RequestMethod.GET)
    public String infoCenter() { return "usercenter/infoCenter"; }

    @RequestMapping(value = "/usercenter/goodsManage", method = RequestMethod.GET)
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

    @RequestMapping(value = "/usercenter/goodsPublish", method = RequestMethod.GET)
    public String goodsPublish(){
        return "usercenter/myseller/goodsPublish";
    }
    @RequestMapping(value = "/usercenter/myAttention", method = RequestMethod.GET)
    public String myAttention(){
        return "usercenter/myseller/myAttention";
    }

    @RequestMapping(value = "/usercenter/goodsEdit", method = RequestMethod.GET)
    public String goodsEdit(){
        return "usercenter/myseller/goodsEdit";
    }
    @RequestMapping(value = "/usercenter/admin", method = RequestMethod.GET)
    public String admin(){
        return "usercenter/userAdmin/index";
    }

}
