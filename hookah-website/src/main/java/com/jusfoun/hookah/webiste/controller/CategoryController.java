package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.CategoryVo;
import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CategoryService;
import com.jusfoun.hookah.rpc.api.MgCategoryAttrTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjl on 2017-3-15.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    protected final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Resource
    CategoryService categoryService;
    @Resource
    MgCategoryAttrTypeService mgCategoryAttrTypeService;

    /**
     * 查询所有分类
     * @param catSign ： 0 普通； 1 系统
     * @return
     */
    @RequestMapping(value = "/findAll/{catSign}", method= RequestMethod.GET)
    public ReturnData findAll(@PathVariable String catSign) {
        ReturnData<List<Category>> returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try{
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("catSign", catSign));
            filters.add(Condition.eq("isShow", 1));
            filters.add(Condition.eq("isDelete", 1));
            List<Category> list = (List) categoryService.selectList(filters);
            returnData.setData(list);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("/findAll")
    public ReturnData findAll() {
        ReturnData<List<CategoryVo>> returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try{
            returnData.setData(categoryService.getCatTree());
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 查询商品分类
     * catSign: 0 普通； 1 系统
     * @param pid
     * @return
     */
    @RequestMapping("/findByPId/{catSign}")
    public ReturnData findByPId(String pid, @PathVariable Byte catSign) {
        ReturnData<List<Category>> returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("parentId", pid));
            filters.add(Condition.eq("catSign", catSign));
            filters.add(Condition.eq("isShow", 1));
            List<Category> list = (List) categoryService.selectList(filters);
            returnData.setData(list);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 创建商品时，查看商品某分类下包括的商品属性
     * @param catId 商品某分类
     * @return
     */
    @RequestMapping("/findAttr")
    public ReturnData findGoodsAttr(String catId) {
        ReturnData<MgCategoryAttrType> returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            MgCategoryAttrType obj = mgCategoryAttrTypeService.findGoodsAttr(catId);
            returnData.setData(obj);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

}
