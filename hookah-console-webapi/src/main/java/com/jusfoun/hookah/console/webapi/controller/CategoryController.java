package com.jusfoun.hookah.console.webapi.controller;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.ResultJson;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.rpc.api.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by wangjl on 2017-3-16.
 */
//@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 增加分类
     * @param category
     * @return
     */
    @RequestMapping("add")
    public ResultJson addCategory(Category category) {
        ResultJson<List<Category>> resultJson = new ResultJson<>();
        resultJson.setCode(HookahConstants.RESULT_SUCCESS);
        try {
            category.setCatId("101");
            int i = categoryService.insert(category);
            if(i < 0) {
                throw new HookahException("操作失败");
            }
        }catch (Exception e) {
            resultJson.setCode(HookahConstants.RESULT_ERROR);
            resultJson.setMessage(e.toString());
            e.printStackTrace();
        }
        return resultJson;
    }
    //TODO delete
    //TODO update
    //TODO select
}
