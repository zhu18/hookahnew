package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.WxUserRecommend;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendCountVo;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ndf on 2017/9/25.
 */
public interface WXUserRecommendService extends GenericService<WxUserRecommend,String> {

    WXUserRecommendCountVo countInviteeAndReward(String userId);

    Pagination<WXUserRecommendVo> findRecommendListByCondition(HashMap<String, Object> paramMap);

    void updateWXUserRecommendIsAuthenticate(String inviteeId);

    void updateWXUserRecommendIsDeal(String inviteeId);

    WXUserRecommendVo findRecommendByRecommenderId(String recommenderId);

    Pagination<WXUserRecommendVo> findRecommendDetailsByRecommenderId(HashMap<String, Object> paramMap);
}
