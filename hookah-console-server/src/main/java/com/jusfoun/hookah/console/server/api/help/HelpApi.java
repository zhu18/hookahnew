package com.jusfoun.hookah.console.server.api.help;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Help;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.HelpService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/10 下午4:47
 * @desc
 */
@RestController
@RequestMapping("/api/help")
public class HelpApi {

    @Resource
    HelpService helpService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object all(HttpServletRequest request) {
        List<Help> helpTreeList = helpService.selectTree();
        return helpTreeList;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ReturnData add(Help help) {
//        help.setCreatorId();
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String,String> user = (HashMap<String,String>)session.getAttribute("user");
        help.setCreatorId(user.get("userId"));
        help.setCreatorName(user.get("userName"));
        help.setAddTime(new Date());
        Help result = helpService.insert(help);
        return ReturnData.success(result);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ReturnData delete(@PathVariable("id") String helpId) {
        try {
            helpService.delete(helpId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success();
    }

    @RequestMapping(value = "/category/add", method = RequestMethod.POST)
    public ReturnData addCategory(Help help) {
//        help.setCreatorId();
        help.setAddTime(new Date());
        help.setParentId("0");
        Help result = helpService.insert(help);
        return ReturnData.success(result);
    }

    @RequestMapping(value = "/category/category", method = RequestMethod.GET)
    public ReturnData category(String currentPage, String pageSize) {
        Pagination<Help> page = new Pagination<>();

        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("parentId", "0"));
        List<OrderBy> orderBys = new ArrayList();
        orderBys.add(OrderBy.desc("addTime"));

        int pageNumberNew = HookahConstants.PAGE_NUM;
        if (StringUtils.isNotBlank(currentPage)) {
            pageNumberNew = Integer.parseInt(currentPage);
        }
        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
        }
        page = helpService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        return ReturnData.success(page);
    }

    @RequestMapping(value = "/category/delete/{id}", method = RequestMethod.POST)
    public ReturnData categoryDelete(@PathVariable("id") String helpId) {
        try {
            helpService.delete(helpId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success();
    }

}
