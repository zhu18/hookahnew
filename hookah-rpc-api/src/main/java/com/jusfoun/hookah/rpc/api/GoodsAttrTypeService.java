package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.GoodsAttrType;
import com.jusfoun.hookah.core.domain.vo.GoodsAttrTypeVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * Created by wangjl on 2017-3-16.
 */
public interface GoodsAttrTypeService extends GenericService<GoodsAttrType, String> {
    List<GoodsAttrTypeVo> findTree();
}
