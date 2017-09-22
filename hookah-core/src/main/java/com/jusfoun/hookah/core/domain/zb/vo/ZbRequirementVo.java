package com.jusfoun.hookah.core.domain.zb.vo;

import com.jusfoun.hookah.core.domain.zb.ZbAnnex;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;

import java.util.List;

/**
 * Created by computer on 2017/9/19.
 */
public class ZbRequirementVo {

    private ZbRequirement zbRequirement;

    private Double rewardMoney;

    private List<ZbAnnex> annex;

    public ZbRequirement getZbRequirement() {
        return zbRequirement;
    }

    public void setZbRequirement(ZbRequirement zbRequirement) {
        this.zbRequirement = zbRequirement;
    }

    public List<ZbAnnex> getAnnex() {
        return annex;
    }

    public void setAnnex(List<ZbAnnex> annex) {
        this.annex = annex;
    }

    public Double getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(Double rewardMoney) {
        this.rewardMoney = rewardMoney;
    }
}
