package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.ResultJson;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.rpc.api.CategoryService;
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
    @Resource
    CategoryService categoryService;

    /**
     * 查询商品分类及商品属性
     * type: 0 商品分类； 1 商品属性
     * catSign: 0 普通； 1 系统
     * @param pid
     * @param type
     * @return
     */
    @RequestMapping("findByPId/{type}/{catSign}")
    public ResultJson findByPId(String pid, @PathVariable Byte type, @PathVariable Byte catSign) {
        ResultJson<List<Category>> resultJson = new ResultJson<>();
        resultJson.setCode(HookahConstants.RESULT_SUCCESS);
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("parentId", pid));
            filters.add(Condition.eq("type", type));
            filters.add(Condition.eq("catSign", catSign));
            filters.add(Condition.eq("isShow", 1));
            List<Category> list = (List) categoryService.selectList(filters);
            resultJson.setData(list);
        }catch (Exception e) {
            resultJson.setCode(HookahConstants.RESULT_ERROR);
            resultJson.setMessage(e.toString());
            e.printStackTrace();
        }
        return resultJson;
    }
}
