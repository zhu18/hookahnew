package com.jusfoun.hookah.console.server.api.category;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.CategoryVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ctp on 2017-4-16.
 */
@RestController
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
        return categoryService.addCat(category);
    }
    //TODO delete

    @RequestMapping("allTree")
    public ReturnData selectTree(){
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<CategoryVo> goodsAttrTypeVos = categoryService.getAdminCatTree();
            if(goodsAttrTypeVos == null) {
                throw new HookahException("操作失败");
            }
            returnData.setData(goodsAttrTypeVos);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @RequestMapping("edit")
    public ReturnData editCategory(Category category) {
        return categoryService.editCat(category);
    }


    @RequestMapping("delete/{cateId}")
    public ReturnData deleteCategory(@PathVariable String cateId) {
        return categoryService.deleteById(cateId);
    }
}
