package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.GoodsStorage;

/**
 * Created by wangjl on 2017-10-28.
 */
public class GoodsStorageVo extends GoodsStorage {
    private MgGoodsStorageVo mgGoodsStorageVo;

    public MgGoodsStorageVo getMgGoodsStorageVo() {
        return mgGoodsStorageVo;
    }

    public void setMgGoodsStorageVo(MgGoodsStorageVo mgGoodsStorageVo) {
        this.mgGoodsStorageVo = mgGoodsStorageVo;
    }
}
