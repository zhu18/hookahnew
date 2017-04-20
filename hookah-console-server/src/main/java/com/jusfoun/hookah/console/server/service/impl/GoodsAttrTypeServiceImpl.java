package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.GoodsAttrTypeMapper;
import com.jusfoun.hookah.core.domain.GoodsAttrType;
import com.jusfoun.hookah.core.domain.vo.GoodsAttrTypeVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.GoodsAttrTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by wangjl on 2017-3-16.
 */
@Service
public class GoodsAttrTypeServiceImpl extends GenericServiceImpl<GoodsAttrType, String> implements GoodsAttrTypeService {
    @Resource
    private GoodsAttrTypeMapper goodsAttrTypeMapper;

    //顶级父类Id
    private final static String TOP_PARENT_ID = "0";

    //商品属性状态
    private final static  Byte  ATTR_TYPE_INVALID = 0;
    private final static  Byte  ATTR_TYPE_VALID = 1;

    @Resource
    public void setDao(GoodsAttrTypeMapper goodsAttrTypeMapper) {
        super.setDao(goodsAttrTypeMapper);
    }

    @Cacheable(value = "goodsAttrInfo")
    @Override
    public GoodsAttrType selectById(String id) {
        return goodsAttrTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<GoodsAttrTypeVo> findTree() {

        List<GoodsAttrTypeVo> goodsAttrTypeVos = new ArrayList<GoodsAttrTypeVo>();

        //查询一级分类的属性
        List<GoodsAttrType> goodsAttrTypes = findByPerentId(TOP_PARENT_ID);

        for(GoodsAttrType goodsAttrType:goodsAttrTypes){
            goodsAttrTypeVos.add(getChildTree(goodsAttrType));
        }

        return goodsAttrTypeVos;
    }

    //查询子类属性集合
    private List<GoodsAttrType> findByPerentId(String parentId){
        List<Condition> filters = new ArrayList<Condition>();
        filters.add(Condition.eq("typeStatus",ATTR_TYPE_VALID));
        filters.add(Condition.eq("parentId",parentId));
        return super.selectList(filters);
    }

    //构造属性树
    private GoodsAttrTypeVo getChildTree(GoodsAttrType goodsAttrType){

        GoodsAttrTypeVo vo = new GoodsAttrTypeVo();
        BeanUtils.copyProperties(goodsAttrType, vo);

        List<GoodsAttrType> childGoodsAttrs =  findByPerentId(goodsAttrType.getTypeId());

        if(Objects.nonNull(childGoodsAttrs) && childGoodsAttrs.size() > 0){
//            vo.setChildren(childGoodsAttrs);//添加子节点信息
            for(GoodsAttrType goodsAttrType1 : childGoodsAttrs){
                GoodsAttrType goodsAttrType2 = getChildTree(goodsAttrType1);//递归子节点数据
                vo.getChildren().add(goodsAttrType2);
            }
        }
        return vo;
    }
}
