package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.SysNews;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.rpc.api.SysNewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/5 上午10:49
 * @desc
 */
@Controller
public class AdminController {

    @Resource
    SysNewsService sysNewsService;

    @RequestMapping(value = "/admin/articleManagement", method = RequestMethod.GET)
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
        model.addAttribute("pageInfo",page);
        return "admin/articleManagement";
    }
}
