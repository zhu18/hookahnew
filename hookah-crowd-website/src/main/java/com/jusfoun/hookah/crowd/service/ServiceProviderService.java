package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementPageHelper;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by ctp on 2017/10/10.
 */
public interface ServiceProviderService extends GenericService<ZbRequirement, Long> {

    /**
     *服务商 - 需求大厅
     */
    ReturnData getRequirementVoList(ZbRequirementPageHelper helper);

    /**
     *服务商 - 我的任务
     */
    ReturnData getRequirementVoListByUserId(ZbRequirementPageHelper helper);

    /**
     *服务商 - 我的任务 - 详情
     */
    ReturnData findByRequirementId(Long reqId);

}
