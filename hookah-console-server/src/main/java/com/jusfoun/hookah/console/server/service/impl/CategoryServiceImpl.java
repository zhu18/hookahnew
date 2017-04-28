package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.CategoryMapper;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.CategoryVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StrUtil;
import com.jusfoun.hookah.rpc.api.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Override
    public ReturnData addCat(Category category) {
        ReturnData<Category> returnData = new ReturnData<Category>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String parentId = category.getParentId();
            if(StringUtils.isBlank(parentId)){
                returnData.setCode(ExceptionConst.AssertFailed);
                returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
                return returnData;
            }
            if("0".equals(parentId)){
                category.setLevel((byte)1);
            }else{
                //获取父类的层级
                Category parentCat = super.selectById(parentId);
                category.setLevel((byte)(1 + parentCat.getLevel()));
            }
            //获取当前父节点下的属性的最大Id值
            String maxId = categoryMapper.findMaxByParentId(parentId);
            if(maxId != null){
                category.setCatId(String.valueOf(Integer.parseInt(maxId) + 1));
            }else{
               //没有查到子节点
                category.setCatId(parentId + "101");
            }
            category = super.insert(category);
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

    @Override
    public ReturnData editCat(Category category) {
        ReturnData<Category> returnData = new ReturnData<Category>();
        returnData.setCode(ExceptionConst.Success);

        try {
            String cateId = category.getCatId();
            if(StringUtils.isBlank(cateId)){
                returnData.setCode(ExceptionConst.AssertFailed);
                returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
                return returnData;
            }

            super.updateByIdSelective(category);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return returnData;
    }

    @Override
    public ReturnData deleteById(String cateId) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {
            if(StringUtils.isBlank(cateId)){
                returnData.setCode(ExceptionConst.AssertFailed);
                returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
                return returnData;
            }
           int number = super.delete(cateId);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }
}
