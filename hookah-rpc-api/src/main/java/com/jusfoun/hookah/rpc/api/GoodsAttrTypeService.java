package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.GoodsAttrType;
import com.jusfoun.hookah.core.domain.vo.GoodsAttrTypeVo;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.List;

/**
 * Created by wangjl on 2017-3-16.
 */
public interface GoodsAttrTypeService extends GenericService<GoodsAttrType, String> {
    /**
     * 构建属性树
     * @return
     */
    List<GoodsAttrTypeVo> findTree();

    /**
     * 添加属性
     * @param goodsAttrType
     * @return
     */
    ReturnData addAttr(GoodsAttrType goodsAttrType);

    public ReturnData editAttrType(GoodsAttrType attrType);

    public ReturnData deleteById(String typeId,String parentId);

}
