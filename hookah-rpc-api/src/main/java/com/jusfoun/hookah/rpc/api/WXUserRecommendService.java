package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.WxUserRecommend;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.HashMap;

/**
 * Created by ndf on 2017/9/25.
 */
public interface WXUserRecommendService extends GenericService<WxUserRecommend,String> {

    HashMap<String,Integer> countInviteeAndReward(String userId);

    Pagination<WXUserRecommendVo> findRecommendListByCondition(HashMap<String, Object> paramMap);

    void updateWXUserRecommendIsAuthenticate(String inviteeId);

    void updateWXUserRecommendIsDeal(String inviteeId);
}
