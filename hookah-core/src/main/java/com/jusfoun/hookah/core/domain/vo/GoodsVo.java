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

    private MgGoods.DataModelBean dataModel;
    private MgGoods.ApplicationSceneBean applicationScene;
    private MgGoods.AloneSoftwareBean aloneSoftware;
    private MgGoods.SaaSBean saaS;
    private String otherDesc;

    private boolean orNotFavorite;
    private String userId;
    private String catName;
    private String checkReason;
    private Integer rowStart;
    private Integer rowEnd;
    private String areaCountry;
    private String areaProvince;
    private String areaCity;
    private String catFullName;
    private Long clickRate;
    private Double goodsGrades;

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

    public boolean isOrNotFavorite() {
        return orNotFavorite;
    }

    public void setOrNotFavorite(boolean orNotFavorite) {
        this.orNotFavorite = orNotFavorite;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String checkReason) {
        this.checkReason = checkReason;
    }

    public Integer getRowStart() {
        return rowStart;
    }

    public void setRowStart(Integer rowStart) {
        this.rowStart = rowStart;
    }

    public Integer getRowEnd() {
        return rowEnd;
    }

    public void setRowEnd(Integer rowEnd) {
        this.rowEnd = rowEnd;
    }

    public String getAreaCountry() {
        return areaCountry;
    }

    public void setAreaCountry(String areaCountry) {
        this.areaCountry = areaCountry;
    }

    public String getAreaProvince() {
        return areaProvince;
    }

    public void setAreaProvince(String areaProvince) {
        this.areaProvince = areaProvince;
    }

    public String getAreaCity() {
        return areaCity;
    }

    public void setAreaCity(String areaCity) {
        this.areaCity = areaCity;
    }

    public String getCatFullName() {
        return catFullName;
    }

    public void setCatFullName(String catFullName) {
        this.catFullName = catFullName;
    }

    public Long getClickRate() {
        return clickRate;
    }

    public void setClickRate(Long clickRate) {
        this.clickRate = clickRate;
    }

    public Double getGoodsGrades() {
        return goodsGrades;
    }

    public void setGoodsGrades(Double goodsGrades) {
        this.goodsGrades = goodsGrades;
    }

    public MgGoods.DataModelBean getDataModel() {
        return dataModel;
    }

    public void setDataModel(MgGoods.DataModelBean dataModel) {
        this.dataModel = dataModel;
    }

    public MgGoods.ApplicationSceneBean getApplicationScene() {
        return applicationScene;
    }

    public void setApplicationScene(MgGoods.ApplicationSceneBean applicationScene) {
        this.applicationScene = applicationScene;
    }

    public MgGoods.AloneSoftwareBean getAloneSoftware() {
        return aloneSoftware;
    }

    public void setAloneSoftware(MgGoods.AloneSoftwareBean aloneSoftware) {
        this.aloneSoftware = aloneSoftware;
    }

    public MgGoods.SaaSBean getSaaS() {
        return saaS;
    }

    public void setSaaS(MgGoods.SaaSBean saaS) {
        this.saaS = saaS;
    }

    public String getOtherDesc() {
        return otherDesc;
    }

    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }
}
