package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.console.server.util.DictionaryUtil;
import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.MgCategoryAttrTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

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
}
