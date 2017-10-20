package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRecommend;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRecommendVo;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.List;

public interface ZbRecommendMapper extends GenericDao<ZbRecommend> {

    int insertAndGetId(ZbRequirement zbRequirement);

    List<ZbRecommendVo> selectRecommendTasksInfo(Object[] objects);

    List<ZbRecommendVo> selectRecommendTasksInfoNo();
}