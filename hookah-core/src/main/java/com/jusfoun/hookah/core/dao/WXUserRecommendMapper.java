package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.WXUserRecommend;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendVo;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.HashMap;
import java.util.List;

public interface WXUserRecommendMapper extends GenericDao<WXUserRecommend> {

    HashMap<String,Integer> countInviteeAndReward(String userId);

    List<WXUserRecommendVo> getRecommendList(HashMap<String, Object> paramMap);

    int countRecommendList(HashMap<String, Object> paramMap);

    WXUserRecommend selectByInviteeId(String userId);

    void updateByInviteeidSelective(WXUserRecommend recommend);

//    WXUserRecommend insertAndGetEntry(WXUserRecommend wxUserRecommend);
}