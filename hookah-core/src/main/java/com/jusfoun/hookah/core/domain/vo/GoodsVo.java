package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-3-17.
 */
public class GoodsVo extends Goods implements Serializable {
    private List<MgCategoryAttrType.AttrTypeBean> attrTypeList;
    private List<MgGoods.FormatBean> formatList;
    private List<MgGoods.ImgBean> imgList;
    private MgGoods.ApiInfoBean apiInfo;

    public List<MgCategoryAttrType.AttrTypeBean> getAttrTypeList() {
        return attrTypeList;
    }

    public void setAttrTypeList(List<MgCategoryAttrType.AttrTypeBean> attrTypeList) {
        this.attrTypeList = attrTypeList;
    }

    public List<MgGoods.FormatBean> getFormatList() {
        return formatList;
    }

    public void setFormatList(List<MgGoods.FormatBean> formatList) {
        this.formatList = formatList;
    }

    public List<MgGoods.ImgBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<MgGoods.ImgBean> imgList) {
        this.imgList = imgList;
    }

    public MgGoods.ApiInfoBean getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(MgGoods.ApiInfoBean apiInfo) {
        this.apiInfo = apiInfo;
    }
}
