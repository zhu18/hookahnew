package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementPageHelper;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.List;

public interface ZbRequirementMapper extends GenericDao<ZbRequirement> {

    int insertAndGetId(ZbRequirement zbRequirement);

    int countRequirementList(ZbRequirementPageHelper helper);

    List<ZbRequirement> getRequirementList(ZbRequirementPageHelper helper);
}