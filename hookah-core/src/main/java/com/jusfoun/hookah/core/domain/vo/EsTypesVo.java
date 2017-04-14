package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.GoodsAttrType;
import com.jusfoun.hookah.core.domain.Region;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-4-10.
 */
public class EsTypesVo implements Serializable {
    private List<EsTreeVo<Category>> categoryList;
    private List<EsTreeVo<GoodsAttrType>> goodsAttrTypeList;
    private List<EsTreeVo<Region>> areaCountryList;
    private List<EsTreeVo<Region>> areaProvinceList;
    private List<EsTreeVo<Region>> areaCityList;

    public List<EsTreeVo<Category>> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<EsTreeVo<Category>> categoryList) {
        this.categoryList = categoryList;
    }

    public List<EsTreeVo<GoodsAttrType>> getGoodsAttrTypeList() {
        return goodsAttrTypeList;
    }

    public void setGoodsAttrTypeList(List<EsTreeVo<GoodsAttrType>> goodsAttrTypeList) {
        this.goodsAttrTypeList = goodsAttrTypeList;
    }

    public List<EsTreeVo<Region>> getAreaCountryList() {
        return areaCountryList;
    }

    public void setAreaCountryList(List<EsTreeVo<Region>> areaCountryList) {
        this.areaCountryList = areaCountryList;
    }

    public List<EsTreeVo<Region>> getAreaProvinceList() {
        return areaProvinceList;
    }

    public void setAreaProvinceList(List<EsTreeVo<Region>> areaProvinceList) {
        this.areaProvinceList = areaProvinceList;
    }

    public List<EsTreeVo<Region>> getAreaCityList() {
        return areaCityList;
    }

    public void setAreaCityList(List<EsTreeVo<Region>> areaCityList) {
        this.areaCityList = areaCityList;
    }
}
