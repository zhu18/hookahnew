package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.WxUserRecommend;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendCountVo;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendVo;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WXUserRecommendMapper extends GenericDao<WxUserRecommend> {

    WXUserRecommendCountVo countInviteeAndReward(String userId);

    List<WXUserRecommendVo> getRecommendList(HashMap<String, Object> paramMap);

    int countRecommendList(HashMap<String, Object> paramMap);

    WxUserRecommend selectByInviteeId(String userId);

    void updateByInviteeidSelective(WxUserRecommend recommend);

    WXUserRecommendVo selectRecommendByRecommenderId(String recommenderId);

    List<WXUserRecommendVo> findRecommendDetailsByRecommenderId(HashMap<String, Object> paramMap);

    int countRecommendDetailsByRecommenderId(HashMap<String, Object> paramMap);

    int selectSumRewardJfByRecommenderId(String recommendId);

//    WxUserRecommend insertAndGetEntry(WxUserRecommend wxUserRecommend);
}