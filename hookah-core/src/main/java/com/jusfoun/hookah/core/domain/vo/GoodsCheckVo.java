package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsCheck;
import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-3-17.
 */
public class GoodsCheckVo implements Serializable {

    private GoodsCheck goodsCheck;

    private MgGoods.PackageApiInfoBean apiInfoBean;

    public GoodsCheck getGoodsCheck() {
        return goodsCheck;
    }

    public void setGoodsCheck(GoodsCheck goodsCheck) {
        this.goodsCheck = goodsCheck;
    }

    public MgGoods.PackageApiInfoBean getApiInfoBean() {
        return apiInfoBean;
    }

    public void setApiInfoBean(MgGoods.PackageApiInfoBean apiInfoBean) {
        this.apiInfoBean = apiInfoBean;
    }
}
