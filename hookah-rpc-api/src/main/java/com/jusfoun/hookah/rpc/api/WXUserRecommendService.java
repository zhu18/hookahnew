package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.WXUserRecommend;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.HashMap;

/**
 * Created by admin on 2017/9/25.
 */
public interface WXUserRecommendService extends GenericService<WXUserRecommend,String> {

    HashMap<String,Integer> countInviteeAndReward(String userId);
}
