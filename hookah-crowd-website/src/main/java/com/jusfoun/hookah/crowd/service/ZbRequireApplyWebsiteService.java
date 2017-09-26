package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by ctp on 2017/9/26.
 */
public interface ZbRequireApplyWebsiteService extends GenericService<ZbRequirementApply, Long> {

    ReturnData addApplay(ZbRequirementApply zbRequirementApply);

}
