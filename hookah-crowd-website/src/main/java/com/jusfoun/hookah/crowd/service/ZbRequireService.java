package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.generic.GenericService;

public interface ZbRequireService extends GenericService<ZbRequirement, Long> {

    int insertRecord(ZbRequirement zbRequirement);
}
