package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * @author huang lei
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface MgCategoryAttrTypeService extends GenericService<MgCategoryAttrType,String> {

    MgCategoryAttrType findGoodsAttr(String catId);

    ReturnData findGoodsAttrByCatId(String catId);

    ReturnData addMgGoodsAttr(String cateId,String attrTypeId);

    ReturnData removeMgGoodsAttr(String cateId,String attrTypeId);

}
