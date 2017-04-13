package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.CategoryMapper;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.CategoryVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 分类查询
 * Created by wangjl on 2017-3-15.
 */
@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, String> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;


    @Resource
    public void setDao(CategoryMapper categoryMapper) {
        super.setDao(categoryMapper);
    }

    @Cacheable(value = "categoryInfo")
    @Override
    public Category selectById(String id) {
        return categoryMapper.selectByPrimaryKey(id);
    }


    public List<CategoryVo> getCatTree() {
        List<CategoryVo> vo = new ArrayList<>();
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("catSign", 1));
        filters.add(Condition.eq("parentId", "0"));
        List<Category> list = super.selectList(filters);
        if(list != null && list.size() > 0) {
            for(Category cat : list) {
                vo.add(getChild(cat.getCatId()));
            }
        }
        return vo;
    }

    private CategoryVo getChild(String pid) {
        List<Condition> filters1 = new ArrayList<>();
        filters1.add(Condition.eq("catSign", 1));
        filters1.add(Condition.eq("catId", pid));
        Category treeNode = super.selectOne(filters1);
        CategoryVo nodeVo = new CategoryVo();
        BeanUtils.copyProperties(treeNode, nodeVo);

        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("catSign", 1));
        filters.add(Condition.eq("parentId", pid));
        List<Category> childList = super.selectList(filters);
        if(childList != null && childList.size() > 0) {
            for(Category cat : childList){
                Category n = getChild(cat.getCatId()); //递归
                CategoryVo nodeVo2 = new CategoryVo();
                BeanUtils.copyProperties(n, nodeVo2);
                nodeVo.getChildren().add(n);
            }
        }
        return nodeVo;
    }
}
