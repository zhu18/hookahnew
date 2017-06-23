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

    private MgGoods.DataModelBean dataModel;                //数据模型
    private MgGoods.ASSaaSBean asSaaS;                      // 应用场景SaaS
    private MgGoods.ASAloneSoftwareBean asAloneSoftware;    // 应用场景 独立软件
    private MgGoods.ATSaaSBean atSaaS;                      // 分析工具 SaaS
    private MgGoods.ATAloneSoftwareBean atAloneSoftware;    // 分析工具 独立软件
    private MgGoods.OffLineInfoBean offLineInfo; //线下交付信息
    private MgGoods.OffLineDataBean offLineData;//离线数据信息

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

    public MgGoods.ASSaaSBean getAsSaaS() {
        return asSaaS;
    }

    public void setAsSaaS(MgGoods.ASSaaSBean asSaaS) {
        this.asSaaS = asSaaS;
    }

    public MgGoods.ASAloneSoftwareBean getAsAloneSoftware() {
        return asAloneSoftware;
    }

    public void setAsAloneSoftware(MgGoods.ASAloneSoftwareBean asAloneSoftware) {
        this.asAloneSoftware = asAloneSoftware;
    }

    public MgGoods.ATSaaSBean getAtSaaS() {
        return atSaaS;
    }

    public void setAtSaaS(MgGoods.ATSaaSBean atSaaS) {
        this.atSaaS = atSaaS;
    }

    public MgGoods.ATAloneSoftwareBean getAtAloneSoftware() {
        return atAloneSoftware;
    }

    public void setAtAloneSoftware(MgGoods.ATAloneSoftwareBean atAloneSoftware) {
        this.atAloneSoftware = atAloneSoftware;
    }

    public MgGoods.OffLineInfoBean getOffLineInfo() {
        return offLineInfo;
    }

    public void setOffLineInfo(MgGoods.OffLineInfoBean offLineInfo) {
        this.offLineInfo = offLineInfo;
    }

    public MgGoods.OffLineDataBean getOffLineData() {
        return offLineData;
    }

    public void setOffLineData(MgGoods.OffLineDataBean offLineData) {
        this.offLineData = offLineData;
    }
}
