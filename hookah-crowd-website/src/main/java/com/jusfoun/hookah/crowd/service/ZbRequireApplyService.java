package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by lt on 2017/9/19.
 */
public interface ZbRequireApplyService extends GenericService<ZbRequirementApply, Long> {

    int insertRecord(ZbRequirementApply zbRequirementApply);

    ReturnData viewApplyByRequire(Integer id) throws Exception;
}
