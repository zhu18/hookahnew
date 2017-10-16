package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbRecommend;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

public interface ZbRecommendService extends GenericService<ZbRecommend, Long> {

    ReturnData selectRecommendTasksInfo();
}
