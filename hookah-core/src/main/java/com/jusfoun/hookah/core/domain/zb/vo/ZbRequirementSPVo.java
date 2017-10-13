package com.jusfoun.hookah.core.domain.zb.vo;

import com.jusfoun.hookah.core.domain.zb.ZbAnnex;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;

import java.util.List;

/**
 * 服务商查询需求VO
 * Created by ctp on 2017/10/11.
 */
public class ZbRequirementSPVo extends ZbRequirement{

    private List<ZbAnnex> annex;//需求附件

    private Integer operStatus;//可操作状态 是否已报名 或者 报名状态

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

}
