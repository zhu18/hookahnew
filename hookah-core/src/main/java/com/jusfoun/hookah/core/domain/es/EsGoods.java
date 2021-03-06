package com.jusfoun.hookah.core.domain.es;

import com.jusfoun.hookah.core.annotation.EsField;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.HookahConstants.AnalyzeOpt;
import com.jusfoun.hookah.core.constants.HookahConstants.Analyzer;
import com.jusfoun.hookah.core.constants.HookahConstants.TermVector;
import com.jusfoun.hookah.core.constants.HookahConstants.Type;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangjl on 2017-3-28.
 */
public class EsGoods implements Serializable {
    @Id
    @EsField(type = Type.KEYWORD)
    private String goodsId;
    @EsField(analyzeOpt= AnalyzeOpt.ANALYZED, analyzer= Analyzer.LC_INDEX,
            termVector= TermVector.OFFSETS, isStore = true, searchAnalyzer = Analyzer.LC_SEARCH)
    private String goodsName;
    @EsField
    private String goodsBrief;
    @EsField
    private String goodsDesc;
    @EsField
    private String keywords;
    @EsField
    private String goodsImg;
    @EsField(fielddata = true)
    private String catId;
    @EsField(analyzeOpt= AnalyzeOpt.ANALYZED, analyzer= Analyzer.WHITESPACE,
            termVector= TermVector.OFFSETS, isStore = true, searchAnalyzer = Analyzer.WHITESPACE)
    private String catIds;
    @Transient
    @EsField(fielddata = true)
    private String[] attrId;
    @Transient
    @EsField(analyzeOpt= AnalyzeOpt.ANALYZED, analyzer= Analyzer.WHITESPACE,
            termVector= TermVector.OFFSETS, isStore = true, searchAnalyzer = Analyzer.WHITESPACE)
    private String attrIds;
    @EsField(fielddata = true)
    @Transient
    private String[] attrTypeId;
    @EsField
    private String goodsArea;
    @EsField(analyzeOpt= AnalyzeOpt.ANALYZED, analyzer= Analyzer.WHITESPACE,
            termVector= TermVector.OFFSETS, isStore = true, searchAnalyzer = Analyzer.WHITESPACE)
    private String goodsAreas;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @EsField(type = Type.DATE)
    private Date addTime;
    @Transient
    @EsField(type = Type.COMPLETION, analyzer= Analyzer.LC_INDEX, searchAnalyzer = Analyzer.LC_SEARCH)
    private String suggest;
    @EsField(type = Type.KEYWORD)
    private Long shopPrice;
    @EsField(type = Type.INTEGER)
    private Integer shopNumber;
    @EsField(type = Type.BYTE)
    private Byte shopFormat;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @EsField(type = Type.KEYWORD)
    private Date lastUpdateTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @EsField(fielddata = true)
    @Transient
    private String areaCountry;
    @EsField(fielddata = true)
    @Transient
    private String areaProvince;
    @Transient
    @EsField(fielddata = true)
    private String areaCity;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @EsField(type = Type.DATE, format = "yyyy-MM-dd HH:mm:ss.SSS")
    @EsField(type = Type.DATE)
    private Date onsaleStartDate;
    @Transient
    private String onsaleStartDateField = HookahConstants.ONSALE_START_DATE_FILEDNAME;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsBrief() {
        return goodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getCatIds() {
        return catIds;
    }

    public void setCatIds(String catIds) {
        this.catIds = catIds;
    }

    public String getGoodsArea() {
        return goodsArea;
    }

    public void setGoodsArea(String goodsArea) {
        this.goodsArea = goodsArea;
    }

    public String getGoodsAreas() {
        return goodsAreas;
    }

    public void setGoodsAreas(String goodsAreas) {
        this.goodsAreas = goodsAreas;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public Long getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Long shopPrice) {
        this.shopPrice = shopPrice;
    }

    public Integer getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(Integer shopNumber) {
        this.shopNumber = shopNumber;
    }

    public Byte getShopFormat() {
        return shopFormat;
    }

    public void setShopFormat(Byte shopFormat) {
        this.shopFormat = shopFormat;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String[] getAttrTypeId() {
        return attrTypeId;
    }

    public void setAttrTypeId(String[] attrTypeId) {
        this.attrTypeId = attrTypeId;
    }

    public String getAttrIds() {
        return attrIds;
    }

    public void setAttrIds(String attrIds) {
        this.attrIds = attrIds;
    }

    public String[] getAttrId() {
        return attrId;
    }

    public void setAttrId(String[] attrId) {
        this.attrId = attrId;
    }

    public String getAreaCountry() {
        return areaCountry;
    }

    public String getOnsaleStartDateField() {
        return onsaleStartDateField;
    }

    public void setOnsaleStartDateField(String onsaleStartDateField) {
        this.onsaleStartDateField = onsaleStartDateField;
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

    public Date getOnsaleStartDate() {
        return onsaleStartDate;
    }

    public void setOnsaleStartDate(Date onsaleStartDate) {
        this.onsaleStartDate = onsaleStartDate;
    }

//    public String getOnsaleStartDateField() {
//        return onsaleStartDateField;
//    }
//
//    public void setOnsaleStartDateField(String onsaleStartDateField) {
//        this.onsaleStartDateField = onsaleStartDateField;
//    }

}
