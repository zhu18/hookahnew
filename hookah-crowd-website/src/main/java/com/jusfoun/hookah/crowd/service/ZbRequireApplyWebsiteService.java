package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by ctp on 2017/9/26.
 */
public interface ZbRequireApplyWebsiteService extends GenericService<ZbRequirementApply, Long> {

    /**
     *报名
     */
    ReturnData addApplay(ZbRequirementApply zbRequirementApply);

    /**
     * 报名信息(根据需求Id 查看当前用户的报名信息)
     */
    ReturnData selectByReqId(Long reqId);

}
