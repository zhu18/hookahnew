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

    @RequestMapping(value = "/1/usercenter/safeset", method = RequestMethod.GET)
    public String safeset() { return "1/usercenter/safeset"; }

    @RequestMapping(value = "/1/usercenter/recharge", method = RequestMethod.GET)
    public String recharge1() { return "1/usercenter/recharge"; }

    @RequestMapping(value = "/1/usercenter/withdrawals", method = RequestMethod.GET)
    public String withdrawals1() { return "1/usercenter/withdrawals"; }

    @RequestMapping(value = "/1/usercenter/fundmanage", method = RequestMethod.GET)
    public String fundmanage1() { return "1/usercenter/fundmanage"; }

    @RequestMapping(value = "/1/usercenter/publishArticle", method = RequestMethod.GET)
    public String publishArticle() { return "1/usercenter/publishArticle"; }



    @RequestMapping(value = "/usercenter/userInfo", method = RequestMethod.GET)
    public String userInfo() { return "usercenter/userInfo/userInfo"; }

    @RequestMapping(value = "/usercenter/safeSet", method = RequestMethod.GET)
    public String safeSet() { return "usercenter/userInfo/safeSet"; }

    @RequestMapping(value = "/usercenter/infoCenter", method = RequestMethod.GET)
    public String infoCenter() { return "usercenter/userInfo/infoCenter"; }

    @RequestMapping(value = "/usercenter/infoCenterA", method = RequestMethod.GET)
    public String infoCenterA() { return "usercenter/userInfo/infoCenterA"; }

    @RequestMapping(value = "/usercenter/infoDetail", method = RequestMethod.GET)
    public String infoDetail() { return "usercenter/userInfo/infoDetail"; }

    @RequestMapping(value = "/usercenter/userProfile", method = RequestMethod.GET)
    public String userProfile() { return "usercenter/userInfo/userProfile"; }

    @RequestMapping(value = "/usercenter/fundmanage", method = RequestMethod.GET)
    public String fundmanage() { return "usercenter/userInfo/fundmanage"; }

    @RequestMapping(value = "/usercenter/recharge", method = RequestMethod.GET)
    public String recharge() { return "/usercenter/userInfo/recharge"; }

    @RequestMapping(value = "/usercenter/withdrawals", method = RequestMethod.GET)
    public String withdrawals() { return "/usercenter/userInfo/withdrawals"; }

    @RequestMapping(value = "/usercenter/createOrder", method = RequestMethod.GET)
    public String createOrder() { return "/usercenter/userInfo/createOrder"; }

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

    @RequestMapping(value = "/usercenter/goodsEdit", method = RequestMethod.GET)
    public String goodsEdit(){
        return "usercenter/myseller/goodsEdit";
    }
    @RequestMapping(value = "/usercenter/admin", method = RequestMethod.GET)
    public String admin(){
        return "usercenter/admin/index";
    }
    @RequestMapping(value = "/usercenter/myAttention", method = RequestMethod.GET)
    public String myAttention(){
        return "usercenter/mybuyer/myAttention";
    }

    @RequestMapping(value = "/usercenter/buyer/evaluation", method = RequestMethod.GET)
    public String evaluation(){
        return "usercenter/buyer/evaluation";
    }

    @RequestMapping(value = "usercenter/buyer/cart", method = RequestMethod.GET)
    public String cart(){ return "usercenter/buyer/cart"; }

    @RequestMapping(value = "/usercenter/buyer/orderManagement", method = RequestMethod.GET)
    public String orderManagement(){ return "/usercenter/buyer/orderManagement"; }
    @RequestMapping(value = "/usercenter/invoice", method = RequestMethod.GET)
    public String invoice(){ return "/usercenter/buyer/invoice"; }
    @RequestMapping(value = "/usercenter/invoiceList", method = RequestMethod.GET)
    public String invoiceList(){ return "/usercenter/buyer/invoiceList"; }
    @RequestMapping(value = "/usercenter/info", method = RequestMethod.GET)
    public String info(){ return "/usercenter/buyer/info"; }

    @RequestMapping(value = "usercenter/customer", method = RequestMethod.GET)
    public String customer(){
        return "usercenter/myseller/customer";
    }

    @RequestMapping(value = "usercenter/custom", method = RequestMethod.GET)
    public String custom(){
        return "usercenter/myseller/custom";
    }

    @RequestMapping(value = "usercenter/illegal", method = RequestMethod.GET)
    public String illegal(){
        return "usercenter/myseller/illegal";
    }

    @RequestMapping(value = "usercenter/rate", method = RequestMethod.GET)
    public String rate(){
        return "usercenter/myseller/rate";
    }

    @RequestMapping(value = "usercenter/trade", method = RequestMethod.GET)
    public String trade(){
        return "usercenter/myseller/trade";
    }

    @RequestMapping(value = "usercenter/tradeing", method = RequestMethod.GET)
    public String tradeing(){
        return "usercenter/myseller/tradeing";
    }

    @RequestMapping(value = "usercenter/cash", method = RequestMethod.GET)
    public String cash(){
        return "usercenter/pay/cash";
    }
}
