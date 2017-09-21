package com.jusfoun.hookah.core.domain.zb.vo;

import com.jusfoun.hookah.core.domain.zb.ZbAnnex;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;

import java.io.Serializable;
import java.util.List;

/**
 * Created by computer on 2017/9/19.
 */
public class ZbRequirementVo implements Serializable {

    private ZbRequirement zbRequirement;

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
}
