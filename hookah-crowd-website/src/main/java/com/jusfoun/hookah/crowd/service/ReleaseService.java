package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementFiles;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRequirementVo;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by zhaoshuai on 2017/9/18.
 */
public interface ReleaseService extends GenericService<ZbRequirement, String> {
    ReturnData insertRequirements(ZbRequirementVo vo);

    ReturnData getRequirementInfo(String userId);

    ReturnData getRequirementSubmit(Long id);

}
