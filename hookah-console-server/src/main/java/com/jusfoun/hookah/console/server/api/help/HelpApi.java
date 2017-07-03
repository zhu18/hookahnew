package com.jusfoun.hookah.console.server.api.help;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Help;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.HelpService;
import org.apache.commons.lang3.StringUtils;
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
public class HelpApi extends BaseController {

    @Resource
    HelpService helpService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object all(HttpServletRequest request) {
        List<Help> helpTreeList = helpService.selectTree();
        return helpTreeList;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ReturnData add(Help help) throws HookahException {
        /*Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> user = (HashMap<String, String>) session.getAttribute("user");*/

        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("helpId", help.getHelpId()));
        boolean exists = helpService.exists(filters);
        if(exists==true){
            return ReturnData.error("该分类ID以存在");
        }
        help.setCreatorId(getCurrentUser().getUserId());
        help.setCreatorName(getCurrentUser().getUserName());
        help.setHelpUrl("/help/" + help.getHelpId() + ".html");
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
    public ReturnData addCategory(Help help) throws HookahException{
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("helpId", help.getHelpId()));
        boolean exists = helpService.exists(filters);
        if(exists==true){
            return ReturnData.error("该分类ID以存在");
        }
       /* Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> curUser = (HashMap<String, String>) session.getAttribute("user");*/
        help.setCreatorId(getCurrentUser().getUserId());
        help.setCreatorName(getCurrentUser().getUserName());
        help.setAddTime(new Date());
        help.setParentId("0");
        Help result = helpService.insert(help);
        return ReturnData.success(result);
    }

    @RequestMapping(value = "/category/category", method = RequestMethod.GET)
    public ReturnData category(String currentPage, String pageSize,String name) {
        Pagination<Help> page = new Pagination<>();

        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("parentId", "0"));
        List<OrderBy> orderBys = new ArrayList();
        orderBys.add(OrderBy.desc("addTime"));
        if(StringUtils.isNotBlank(name)){
            filters.add(Condition.like("name", name.trim()));
        }
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

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public ReturnData getCategoryById(@PathVariable("id") String helpId) {
        Help help = helpService.selectById(helpId);
        return ReturnData.success(help);
    }

    @RequestMapping("/category/update")
    public ReturnData updateCategory(Help help){
        if(help.getName().length()>20){
            return ReturnData.error("分类名称最长为20");
        }
        if(StringUtils.isBlank(help.getName())){
            return ReturnData.error("分类名称不可为空");
        }
        String reg = "[\\u4e00-\\u9fa5]+";
        if(!help.getName().matches(reg)){
            return ReturnData.error("分类名称请使用中文");
        }
        try {
            helpService.updateByIdSelective(help);
        } catch (Exception e) {
            return ReturnData.error("修改失败");
        }
        return ReturnData.success("修改成功");
    }
}
