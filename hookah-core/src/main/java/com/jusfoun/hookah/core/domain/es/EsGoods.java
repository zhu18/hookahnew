package com.jusfoun.hookah.core.domain.es;

import com.jusfoun.hookah.core.annotation.EsField;
import com.jusfoun.hookah.core.constants.HookahConstants.AnalyzeOpt;
import com.jusfoun.hookah.core.constants.HookahConstants.Analyzer;
import com.jusfoun.hookah.core.constants.HookahConstants.TermVector;
import com.jusfoun.hookah.core.constants.HookahConstants.Type;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wangjl on 2017-3-28.
 */
public class EsGoods {
    @Id
    @EsField
    private String goodsId;
    @EsField(analyzeOpt= AnalyzeOpt.ANALYZED, analyzer= Analyzer.IK_MAX_WORD,
            termVector= TermVector.OFFSETS, isStore = true, searchAnalyzer = Analyzer.IK_SMART)
    private String goodsName;
    @EsField(analyzeOpt= AnalyzeOpt.ANALYZED, analyzer= Analyzer.PINYIN,
            termVector= TermVector.OFFSETS, isStore = true, searchAnalyzer = Analyzer.PINYIN)
    private String goodsNamePy;
    @EsField
    private String goodsBrief;
    @EsField
    private String goodsDesc;
    @EsField
    private String keywords;
    @EsField
    private String goodsImg;
    @EsField
    private String catId;
    @EsField
    private String attrId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @EsField(type = Type.DATE)
    private Date addTime;

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

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
