package com.jusfoun.hookah.core.domain.zb.vo;

import com.jusfoun.hookah.core.domain.zb.ZbAnnex;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;

import java.util.List;

/**
 * 服务商查询需求VO
 * Created by ctp on 2017/10/11.
 */
public class ZbRequirementSPVo extends ZbRequirement{

    private Double rewardMoneyWebsite;

    private List<ZbAnnex> annex;

    private Integer operStatus;//可操作状态

    public Integer getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(Integer operStatus) {
        this.operStatus = operStatus;
    }


    public List<ZbAnnex> getAnnex() {
        return annex;
    }

    public void setAnnex(List<ZbAnnex> annex) {
        this.annex = annex;
    }

    public Double getRewardMoneyWebsite() {
        return rewardMoneyWebsite;
    }

    public void setRewardMoneyWebsite(Double rewardMoneyWebsite) {
        this.rewardMoneyWebsite = rewardMoneyWebsite;
    }
}
