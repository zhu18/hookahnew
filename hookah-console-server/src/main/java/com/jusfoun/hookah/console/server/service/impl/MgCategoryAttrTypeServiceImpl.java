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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
                        GoodsAttrType goodsAttrType = DictionaryUtil.getAttrById(typeBean.getTypeId());
                        if(null != goodsAttrType){
                            BeanUtils.copyProperties(goodsAttrType, goodsAttrTypeVo);
                        }
                        //获取二级属性
                        List<GoodsAttrType> childGoodsAttrTypes = new ArrayList<GoodsAttrType>();
                        List<MgCategoryAttrType.AttrTypeBean.AttrBean> attrBeans = typeBean.getAttrList();
                        if(attrBeans != null && attrBeans.size() > 0) {
                            for(MgCategoryAttrType.AttrTypeBean.AttrBean attrBean : attrBeans) {
                                GoodsAttrTypeVo childGoodsAttrTypeVo = new GoodsAttrTypeVo();
                                GoodsAttrType childGoodsAttrType = DictionaryUtil.getAttrById(attrBean.getAttrId());
                                if(null != childGoodsAttrType){
                                    BeanUtils.copyProperties(childGoodsAttrType, childGoodsAttrTypeVo);
                                }
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

    @Override
    public ReturnData addMgGoodsAttr(String cateId, String attrTypeId) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try{
            if(StringUtils.isBlank(cateId) || StringUtils.isBlank(attrTypeId)){
                returnData.setCode(ExceptionConst.AssertFailed);
                returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
                return returnData;
            }


            MgCategoryAttrType mgCategoryAttrType = super.selectById(cateId);
            if(null != mgCategoryAttrType){
                List<MgCategoryAttrType.AttrTypeBean> attrTypeBeans = mgCategoryAttrType.getAttrTypeList();
                if(null != attrTypeBeans){ //&& attrTypeBeans.size() > 0
                    GoodsAttrType attrType = DictionaryUtil.getAttrById(attrTypeId);
                    //判断所加属性是否一级属性
                    if(Integer.valueOf(1).equals(attrType.getLevel())){
                        for(MgCategoryAttrType.AttrTypeBean attrTypeBean : attrTypeBeans){
                            /**
                             * 1.添加一级属性是否把二级属性全部加入
                             * 2.是否可以移除一级属性
                             * 3.是否显示一级属性的添加或删除
                             * *****
                             *  解决方法:不显示一级属性的添加和删除可以
                             *   缺陷:只能单个操作
                             */
                            //待开发 看业务需求  添加一级属性下的所有属性
                            if(attrTypeId.equals(attrTypeBean.getTypeId())){

                            }

                        }
                    }else{
                        GoodsAttrType parentAttrType = DictionaryUtil.getAttrById(attrType.getParentId());
                        boolean isExist = false;
                        for(MgCategoryAttrType.AttrTypeBean attrTypeBean : attrTypeBeans){
                            if(parentAttrType.getTypeId().equals(attrTypeBean.getTypeId())){
                                MgCategoryAttrType.AttrTypeBean.AttrBean attrBean = new MgCategoryAttrType.AttrTypeBean.AttrBean();
                                attrBean.setAttrId(attrType.getTypeId());
                                attrBean.setAttrName(attrType.getTypeName());
                                attrTypeBean.getAttrList().add(attrBean);
                                isExist = true;
                                break;
                            }
                        }

                        if(!isExist){
                            MgCategoryAttrType.AttrTypeBean attrTypeBean = buildAttrTypeBean(attrTypeId);
                            attrTypeBeans.add(attrTypeBean);
                        }

                        Query query=new Query(Criteria.where("catId").is(cateId));
                        Update update = new Update();
                        update.set("attrTypeList",attrTypeBeans);
                        mongoTemplate.updateFirst(query,update,mgCategoryAttrType.getClass());
                    }
                }
            }else {
                mgCategoryAttrType = buildMgCateAttrtype(cateId,attrTypeId);
                super.insert(mgCategoryAttrType);
            }

        }catch (Exception e){
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


    @Override
    public ReturnData removeMgGoodsAttr(String cateId, String attrTypeId) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try{
            MgCategoryAttrType mgCategoryAttrType = super.selectById(cateId);
            if(null != mgCategoryAttrType){
                List<MgCategoryAttrType.AttrTypeBean> attrTypeBeans = mgCategoryAttrType.getAttrTypeList();
                if(null != attrTypeBeans && attrTypeBeans.size() > 0){
                    GoodsAttrType attrType = DictionaryUtil.getAttrById(attrTypeId);
                    if(Integer.valueOf(1).equals(attrType.getLevel())){
                        //待开发 看业务需求  移除一级属性下的所有属性
                    }else{
                        GoodsAttrType parentAttrType = DictionaryUtil.getAttrById(attrType.getParentId());
//                        for(MgCategoryAttrType.AttrTypeBean attrTypeBean : attrTypeBeans){}
                        Iterator<MgCategoryAttrType.AttrTypeBean> attrTypeBeanIteratorr = attrTypeBeans.iterator();
                        while (attrTypeBeanIteratorr.hasNext()){
                            MgCategoryAttrType.AttrTypeBean attrTypeBean = attrTypeBeanIteratorr.next();
                            if(parentAttrType.getTypeId().equals(attrTypeBean.getTypeId())){
                                List<MgCategoryAttrType.AttrTypeBean.AttrBean> attrBeans = attrTypeBean.getAttrList();
                                Iterator<MgCategoryAttrType.AttrTypeBean.AttrBean> attrBeanIterator = attrBeans.iterator();
                                while (attrBeanIterator.hasNext()){
                                    if(attrBeanIterator.next().getAttrId().equals(attrTypeId)){
                                        attrBeanIterator.remove();
                                        break;
                                    }
                                }
                                if(attrBeans.size() == 0){
                                    attrTypeBeanIteratorr.remove();
                                }
                                break;
                            }
                        }

                        Query query=new Query(Criteria.where("catId").is(cateId));
                        Update update = new Update();
                        update.set("attrTypeList",attrTypeBeans);
                        mongoTemplate.updateFirst(query,update,mgCategoryAttrType.getClass());
                    }

                }
            }

        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    //构建分类-属性关系
    private MgCategoryAttrType buildMgCateAttrtype(String cateId,String attrTypeId){
        MgCategoryAttrType mgCategoryAttrType =new MgCategoryAttrType();
        mgCategoryAttrType.setCatId(cateId);

        List<MgCategoryAttrType.AttrTypeBean> attrTypeBeans = new ArrayList<MgCategoryAttrType.AttrTypeBean>();

        MgCategoryAttrType.AttrTypeBean attrTypeBean = buildAttrTypeBean(attrTypeId);

        //注入MgCategoryAttrType.AttrTypeBean
        attrTypeBeans.add(attrTypeBean);

        mgCategoryAttrType.setAttrTypeList(attrTypeBeans);
        return  mgCategoryAttrType;
    }

    private MgCategoryAttrType.AttrTypeBean buildAttrTypeBean(String attrTypeId){
        MgCategoryAttrType.AttrTypeBean attrTypeBean = new MgCategoryAttrType.AttrTypeBean();
        GoodsAttrType attrType = DictionaryUtil.getAttrById(attrTypeId);
        if(null != attrType){
            //判断所加属性是一级属性还是二级属性
            if(Integer.valueOf(1).equals(attrType.getLevel())){
                attrTypeBean.setTypeId(attrType.getTypeId());
                attrTypeBean.setTypeName(attrType.getTypeName());
            }else{
                //添加一级属性
                GoodsAttrType parentAttrType = DictionaryUtil.getAttrById(attrType.getParentId());
                attrTypeBean.setTypeId(parentAttrType.getTypeId());
                attrTypeBean.setTypeName(parentAttrType.getTypeName());
                //添加二级属性
                List<MgCategoryAttrType.AttrTypeBean.AttrBean> attrBeans = new ArrayList<MgCategoryAttrType.AttrTypeBean.AttrBean>();
                MgCategoryAttrType.AttrTypeBean.AttrBean attrBean = buildAttrBean(attrType);
                attrBeans.add(attrBean);
                attrTypeBean.setAttrList(attrBeans);
            }
        }
        return attrTypeBean;
    }

    private MgCategoryAttrType.AttrTypeBean.AttrBean buildAttrBean(GoodsAttrType attrType){
        MgCategoryAttrType.AttrTypeBean.AttrBean attrBean = new MgCategoryAttrType.AttrTypeBean.AttrBean();
        attrBean.setAttrId(attrType.getTypeId());
        attrBean.setAttrName(attrType.getTypeName());
        attrBean.setLevel(attrType.getLevel().intValue());
        return attrBean;
    }

}
