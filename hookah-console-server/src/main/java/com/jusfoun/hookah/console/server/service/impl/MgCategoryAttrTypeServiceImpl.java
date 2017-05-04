package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.console.server.util.DictionaryUtil;
import com.jusfoun.hookah.core.domain.GoodsAttrType;
import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.domain.vo.GoodsAttrTypeVo;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.MgCategoryAttrTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjl on 2017-3-16.
 */
public class MgCategoryAttrTypeServiceImpl extends GenericMongoServiceImpl<MgCategoryAttrType, String> implements MgCategoryAttrTypeService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public MgCategoryAttrType findGoodsAttr(String catId) {
        MgCategoryAttrType type = mongoTemplate.findById(catId, MgCategoryAttrType.class);
        if(type != null) {
            List<MgCategoryAttrType.AttrTypeBean> attrTypeList = type.getAttrTypeList();
            if(attrTypeList != null && attrTypeList.size() > 0) {
                for(MgCategoryAttrType.AttrTypeBean typeBean : attrTypeList) {
                    typeBean.setTypeName(DictionaryUtil.getAttrById(typeBean.getTypeId()) == null ?
                            "" : DictionaryUtil.getAttrById(typeBean.getTypeId()).getTypeName());
                    List<MgCategoryAttrType.AttrTypeBean.AttrBean> list = typeBean.getAttrList();
                    if(list != null && list.size() > 0) {
                        for(MgCategoryAttrType.AttrTypeBean.AttrBean attrBean : list) {
                            attrBean.setAttrName(DictionaryUtil.getAttrById(attrBean.getAttrId()) == null ?
                                    "" : DictionaryUtil.getAttrById(attrBean.getAttrId()).getTypeName());;
                        }
                    }
                }
            }
        }
        return type;
    }

    @Override
    public ReturnData findGoodsAttrByCatId(String catId) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {
            if(StringUtils.isBlank(catId)){
                returnData.setCode(ExceptionConst.AssertFailed);
                returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
                return returnData;
            }
            List<GoodsAttrTypeVo> goodsAttrTypeVos = new ArrayList<GoodsAttrTypeVo>();
            MgCategoryAttrType type = mongoTemplate.findById(catId, MgCategoryAttrType.class);
            if(type != null) {
                List<MgCategoryAttrType.AttrTypeBean> attrTypeList = type.getAttrTypeList();
                if(attrTypeList != null && attrTypeList.size() > 0) {
                    //获取一级属性
                    for(MgCategoryAttrType.AttrTypeBean typeBean : attrTypeList) {
                        GoodsAttrTypeVo goodsAttrTypeVo = new GoodsAttrTypeVo();
                        goodsAttrTypeVo.setTypeId(typeBean.getTypeId());
                        goodsAttrTypeVo.setTypeName(typeBean.getTypeName());
                        //获取二级属性
                        List<GoodsAttrType> childGoodsAttrTypes = new ArrayList<GoodsAttrType>();
                        List<MgCategoryAttrType.AttrTypeBean.AttrBean> attrBeans = typeBean.getAttrList();
                        if(attrBeans != null && attrBeans.size() > 0) {
                            for(MgCategoryAttrType.AttrTypeBean.AttrBean attrBean : attrBeans) {
                                GoodsAttrTypeVo childGoodsAttrTypeVo = new GoodsAttrTypeVo();
                                childGoodsAttrTypeVo.setTypeId(attrBean.getAttrId());
                                childGoodsAttrTypeVo.setTypeName(attrBean.getAttrName());
                                childGoodsAttrTypeVo.setLevel((byte) attrBean.getLevel().intValue());
                                childGoodsAttrTypes.add(childGoodsAttrTypeVo);
                            }
                        }
                        goodsAttrTypeVo.setChildren(childGoodsAttrTypes);

                        goodsAttrTypeVos.add(goodsAttrTypeVo);
                    }
                }
            }
            returnData.setData(goodsAttrTypeVos);

        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}
