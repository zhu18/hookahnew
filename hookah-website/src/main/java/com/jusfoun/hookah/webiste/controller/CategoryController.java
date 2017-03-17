package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Category;
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
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjl on 2017-3-15.
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    protected final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Resource
    CategoryService categoryService;
    @Resource
    MgCategoryAttrTypeService mgCategoryAttrTypeService;

    /**
     * 查询商品分类
     * catSign: 0 普通； 1 系统
     * @param pid
     * @return
     */
    @RequestMapping("findByPId/{catSign}")
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
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("findAttr")
    public ReturnData findGoodsAttr(String catId) {
        logger.info("----------------"+catId);
        ReturnData<MgCategoryAttrType> returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            MgCategoryAttrType obj = mgCategoryAttrTypeService.selectById(catId);
            returnData.setData(obj);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

}
