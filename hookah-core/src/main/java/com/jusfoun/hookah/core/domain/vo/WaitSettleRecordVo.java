package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.WaitSettleRecord;

public class WaitSettleRecordVo extends WaitSettleRecord{

    private String orgName;

    private String goodsImg;

    private String goodsSn;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }
}
