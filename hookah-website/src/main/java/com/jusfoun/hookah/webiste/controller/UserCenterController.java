package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.SysNews;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.rpc.api.SysNewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/usercenter", method = RequestMethod.GET)
    public String index(){
        return "usercenter/index";
    }

    @RequestMapping(value = "/usercenter/fundmanage", method = RequestMethod.GET)
    public String fundmanage() { return "usercenter/fundmanage"; }

    @RequestMapping(value = "/usercenter/safeset", method = RequestMethod.GET)
    public String safeset() { return "usercenter/safeset"; }

    @RequestMapping(value = "/usercenter/recharge", method = RequestMethod.GET)
    public String recharge() { return "usercenter/recharge"; }

    @RequestMapping(value = "/usercenter/withdrawals", method = RequestMethod.GET)
    public String withdrawals() { return "usercenter/withdrawals"; }

    @RequestMapping(value = "/usercenter/articleManagement", method = RequestMethod.GET)
    public String articleManagement(Model model) {

        Pagination<SysNews> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("sytTime"));
            //参数校验
            int pageNumberNew = 1;
            int pageSizeNew = 10;
            filters.add(Condition.eq("newsGroup", SysNews.Innovation));
            page = sysNewsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();

        }
        model.addAttribute("list",page);
        return "usercenter/articleManagement";
    }

    @RequestMapping(value = "/usercenter/publishArticle", method = RequestMethod.GET)
    public String publishArticle() { return "usercenter/publishArticle"; }
}
