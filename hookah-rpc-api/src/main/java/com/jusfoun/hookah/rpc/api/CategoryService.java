package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.CategoryVo;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.List;

/**
 * Created by wangjl on 2017-3-15.
 */
public interface CategoryService extends GenericService<Category,String> {

    /**
     * 前台专用
     * @return
     */
    public List<CategoryVo> getCatTree();

    public ReturnData addCat(Category category);

    public ReturnData editCat(Category category);

    public ReturnData deleteById(String catId);

    /**
     * 管理员专用
     * @return
     */
    public List<CategoryVo> getAdminCatTree();
}
