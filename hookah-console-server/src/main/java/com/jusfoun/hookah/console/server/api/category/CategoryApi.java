package com.jusfoun.hookah.console.server.api.category;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by ctp on 2017-4-16.
 */
//@RestController
@RequestMapping("/api/category")
public class CategoryApi {
    @Autowired
    CategoryService categoryService;

    /**
     * 增加分类
     * @param category
     * @return
     */
    @RequestMapping("add")
    public ReturnData addCategory(Category category) {
        ReturnData<List<Category>> returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            category.setCatId("101");
            category = categoryService.insert(category);
            if(category == null) {
                throw new HookahException("操作失败");
            }
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
    //TODO delete
    //TODO update
    //TODO select
}
