package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface ZbRequirementMapper extends GenericDao<ZbRequirement> {

    int insertAndGetId(ZbRequirement zbRequirement);
}