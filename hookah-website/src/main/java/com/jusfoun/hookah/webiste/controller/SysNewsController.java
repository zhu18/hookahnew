package com.jusfoun.hookah.webiste.controller;


import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.SysNews;
import com.jusfoun.hookah.core.domain.vo.SysNewsVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CategoryService;
import com.jusfoun.hookah.rpc.api.SysNewsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;


/**
 * 新闻资讯类接口
 */
@Controller
@RequestMapping("sysNews")
public class SysNewsController {
    protected final static Logger logger = LoggerFactory.getLogger(SysNewsController.class);

    @Resource
    SysNewsService sysNewsService;

    @Resource
    CategoryService categoryService;

    /**
     * 第几页
     */
    private static final int PAGE_NUM = 1;
    /**
     * 每页记录数
     */
    private static final int PAGE_SIZE = 10;

   /* @RequestMapping(value = "/sysNews/list", method = RequestMethod.POST)
    public String list(SysNews model){
        sysNewsService.insert(model);
        return "/goods/list";
    }*/

    /**
     * 根据信息ID获取详情信息
     *
     * @param id
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "details", method = RequestMethod.GET)
    public ReturnData details(String id) {
        ReturnData result = new ReturnData();
        try {
            SysNewsVo sysN = new SysNewsVo();
            sysN = sysNewsService.selectNewsByID(id);
            result.setData(sysN);
            result.success();
        } catch (Exception e) {
            result.setCode("0");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据ID批量删除文章
     *
     * @param ids
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "deleteInfo", method = RequestMethod.POST)
    public ReturnData deleteInfo(@RequestBody String[] ids) {
        try {
            sysNewsService.delete(ids);
        } catch (Exception e) {
            return ReturnData.fail();
        }
        return ReturnData.success();
    }


    /**
     * 根据ID删除文章
     *
     * @param id
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "deleteOne", method = RequestMethod.GET)
    public ReturnData deleteOne(String id) {
        try {
            sysNewsService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.fail();
        }
        return ReturnData.success();
    }


    /**
     * 查询列表
     *
     * @param pageNumber   所在位置
     * @param pageSize     每页记录数
     * @param newsGroup    文章一级分类
     * @param newsSonGroup 文章二级分类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ReturnData list(String pageNumber, String pageSize, String newsGroup, String newsSonGroup) {
        Pagination<SysNews> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("sytTime"));
            //参数校验
            int pageNumberNew = PAGE_NUM;
            if (StringUtils.isNotBlank(pageNumber)) {
                pageNumberNew = Integer.parseInt(pageNumber);
            }

            int pageSizeNew = PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }

            if (StringUtils.isNotBlank(newsGroup)) {
                filters.add(Condition.eq("newsGroup", newsGroup));
            } else {
                filters.add(Condition.eq("newsGroup", SysNews.Innovation));
            }

            if (StringUtils.isNotBlank(newsSonGroup)) {
                filters.add(Condition.eq("newsSonGroup", newsSonGroup));
            }

            page = sysNewsService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.error("exception");
        }
        return ReturnData.success(page);
    }


    /**
     * 查询列表
     *
     * @param pageNumber   所在位置
     * @param pageSize     每页记录数
     * @param newsGroup    文章一级分类
     * @param newsSonGroup 文章二级分类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listByGroup", method = RequestMethod.GET)
    public ReturnData listByGroup(String pageNumber, String pageSize, String newsGroup, String newsSonGroup) {

        try {
            //参数校验
            int pageNumberNew = PAGE_NUM;
            if (StringUtils.isNotBlank(pageNumber)) {
                pageNumberNew = Integer.parseInt(pageNumber);
            }
            int pageSizeNew = PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> infos = sysNewsService.getNewsList(newsGroup, newsSonGroup, pageSizeNew);
            List<List<SysNewsVo>> data = (List<List<SysNewsVo>>) infos.get("dataList");
            map.put("dataList", data.get((pageNumberNew - 1)));
            map.put("totalPage", infos.get("totalCount"));
            map.put("totalItems", infos.get("totalItem"));//总条数
            map.put("pageSize", 10);//每页条数
            map.put("currentPage", pageNumber);//当前页
            return ReturnData.success(map);

        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.error("exception");
        }
    }




 /*   @RequestMapping(value = "/sysNews/getModelById", method = RequestMethod.GET)
    public String getModelById(){
        SysNews model = new SysNews();
        String id = "123";
        model = sysNewsService.selectById(id);
         System.out.print(model.getContent() +" --------------------------------------------------  ");
        return "/goods/details";
    }*/

    /**
     * 新增文章
     *
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public ReturnData insert(SysNews model) {
        ReturnData result = new ReturnData();
        try {
            SysNews snews = new SysNews();
            snews = model;
            snews.setSytTime(new Date());
            sysNewsService.insert(snews);
            Category cy = new Category();
            cy.setAddUser("ddddddddddd");
            categoryService.insert(cy);
            result.success();
        } catch (Exception e) {
            result.setCode("0");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改文章
     *
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ReturnData update(SysNews model) {
        ReturnData result = new ReturnData();
        try {
            SysNews snews = new SysNews();
            snews = model;
            snews.setSytTime(new Date());
            snews.setUpdateTime(new Date());
            sysNewsService.updateById(snews);
            result.success();
        } catch (Exception e) {
            result.setCode("0");
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "select", method = RequestMethod.GET)
    @ResponseBody
    public Object select(Model model) {
        List<Condition> filters = new ArrayList();
     /*   filters.add(Condition.eq("produceType", paramMap.get("produceType")));*/

        List<SysNews> list = (List) sysNewsService.selectList();
        return list;
    }
}
