package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRecommend;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface ZbRecommendMapper extends GenericDao<ZbRecommend> {

    int insertAndGetId(ZbRequirement zbRequirement);
}