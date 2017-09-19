package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbRequirementCheck;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by admin on 2017/9/19.
 */
public interface ZbRequireCheckService extends GenericService<ZbRequirementCheck, Long> {

    ReturnData<ZbRequirementCheck> requirementCheck(ZbRequirementCheck zbRequirementCheck);
}
