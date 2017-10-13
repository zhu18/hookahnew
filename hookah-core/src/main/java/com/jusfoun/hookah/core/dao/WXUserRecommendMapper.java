package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.WxUserRecommend;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendVo;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.HashMap;
import java.util.List;

public interface WXUserRecommendMapper extends GenericDao<WxUserRecommend> {

    HashMap<String,Integer> countInviteeAndReward(String userId);

    List<WXUserRecommendVo> getRecommendList(HashMap<String, Object> paramMap);

    int countRecommendList(HashMap<String, Object> paramMap);

    WxUserRecommend selectByInviteeId(String userId);

    void updateByInviteeidSelective(WxUserRecommend recommend);

//    WxUserRecommend insertAndGetEntry(WxUserRecommend wxUserRecommend);
}