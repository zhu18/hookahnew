package com.jusfoun.hookah.core.domain.es;

import com.jusfoun.hookah.core.annotation.EsField;
import com.jusfoun.hookah.core.constants.HookahConstants.AnalyzeOpt;
import com.jusfoun.hookah.core.constants.HookahConstants.Analyzer;
import com.jusfoun.hookah.core.constants.HookahConstants.TermVector;
import com.jusfoun.hookah.core.constants.HookahConstants.Type;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangjl on 2017-3-28.
 */
public class EsGoods implements Serializable {
    @Id
    @EsField(type = Type.KEYWORD)
    private String goodsId;
    @EsField(analyzeOpt= AnalyzeOpt.ANALYZED, analyzer= Analyzer.IK_MAX_WORD,
            termVector= TermVector.OFFSETS, isStore = true, searchAnalyzer = Analyzer.IK_MAX_WORD, copyTo = "goodsNameAll")
    private String goodsName;
    @EsField(analyzeOpt= AnalyzeOpt.ANALYZED, analyzer= Analyzer.PINYIN,
            termVector= TermVector.OFFSETS, isStore = true, searchAnalyzer = Analyzer.PINYIN, copyTo = "goodsNameAll")
    private String goodsNamePy;
    @EsField(analyzeOpt= AnalyzeOpt.ANALYZED, analyzer= Analyzer.PINYIN, searchAnalyzer = Analyzer.PINYIN)
    private String goodsNameAll;
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
    @EsField(fielddata = true)
    private String[] attrId;
    @EsField(analyzeOpt= AnalyzeOpt.ANALYZED, analyzer= Analyzer.WHITESPACE,
            termVector= TermVector.OFFSETS, isStore = true, searchAnalyzer = Analyzer.WHITESPACE)
    private String attrIds;
    @EsField(fielddata = true)
    private String[] attrTypeId;
    @EsField
    private String goodsArea;
    @EsField(analyzeOpt= AnalyzeOpt.ANALYZED, analyzer= Analyzer.WHITESPACE,
            termVector= TermVector.OFFSETS, isStore = true, searchAnalyzer = Analyzer.WHITESPACE)
    private String goodsAreas;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @EsField(type = Type.DATE)
    private Date addTime;
    @EsField(type = Type.COMPLETION, analyzer= Analyzer.IK_MAX_WORD, searchAnalyzer = Analyzer.IK_MAX_WORD)
    private Suggest suggest;
    @EsField(type = Type.KEYWORD)
    private Long shopPrice;
    @EsField(type = Type.INTEGER)
    private Integer shopNumber;
    @EsField(type = Type.BYTE)
    private Byte shopFormat;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @EsField(type = Type.DATE, copyTo = "lastUpdateTimeKey")
    private Date lastUpdateTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @EsField(type = Type.KEYWORD)
    private Date lastUpdateTimeKey;
    @EsField(fielddata = true)
    private String areaCountry;
    @EsField(fielddata = true)
    private String areaProvince;
    @EsField(fielddata = true)
    private String areaCity;

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

    public String getGoodsNamePy() {
        return goodsNamePy;
    }

    public void setGoodsNamePy(String goodsNamePy) {
        this.goodsNamePy = goodsNamePy;
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

    public String getGoodsNameAll() {
        return goodsNameAll;
    }

    public void setGoodsNameAll(String goodsNameAll) {
        this.goodsNameAll = goodsNameAll;
    }

    public Suggest getSuggest() {
        return suggest;
    }

    public void setSuggest(Suggest suggest) {
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

    public Date getLastUpdateTimeKey() {
        return lastUpdateTimeKey;
    }

    public void setLastUpdateTimeKey(Date lastUpdateTimeKey) {
        this.lastUpdateTimeKey = lastUpdateTimeKey;
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
}
