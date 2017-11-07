package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.CategoryMapper;
import com.jusfoun.hookah.core.dao.GoodsTypeMapper;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.CategoryVo;
import com.jusfoun.hookah.core.domain.GoodsType;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Cacheable(value = "CategoryVo")
    public List<CategoryVo> getCatTree() {
        List<CategoryVo> vo = new ArrayList<>();
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("isShow", Byte.valueOf("1")));
        filters.add(Condition.eq("parentId", "0"));
        List<Category> list = super.selectList(filters);
        if(list != null && list.size() > 0) {
            for(Category cat : list) {
                vo.add(getChild(cat.getCatId(),Byte.valueOf("1")));
            }
        }
        return vo;
    }

    private CategoryVo getChild(String pid,Byte isShow) {
        List<Condition> filters1 = new ArrayList<>();
        if(Objects.nonNull(isShow)){
            filters1.add(Condition.eq("isShow", isShow));
        }
        filters1.add(Condition.eq("catId", pid));
        Category treeNode = super.selectOne(filters1);
        CategoryVo nodeVo = new CategoryVo();
        BeanUtils.copyProperties(treeNode, nodeVo);
/*
        //绑定商品类型名称 ctp 2017.7.6 start
        GoodsType goodsType = goodsTypeMapper.selectByPrimaryKey(treeNode.getDataTemp());
        nodeVo.setGoodsTypeName(goodsType==null?"":goodsType.getTypeName());
        //end*/

        List<Condition> filters = new ArrayList<>();
        if(Objects.nonNull(isShow)){
            filters.add(Condition.eq("isShow", isShow));
        }
        filters.add(Condition.eq("parentId", pid));
        List<Category> childList = super.selectList(filters);
        if(childList != null && childList.size() > 0) {
            for(Category cat : childList){
                Category n;
                if(Objects.nonNull(isShow)){
                    n = getChild(cat.getCatId(),null); //递归
                }else{
                    n = getChild(cat.getCatId(),isShow); //递归
                }

                CategoryVo nodeVo2 = new CategoryVo();
                BeanUtils.copyProperties(n, nodeVo2);
                nodeVo.getChildren().add(n);
            }
        }
        return nodeVo;
    }

}
