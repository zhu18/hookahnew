package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.WXUserRecommendMapper;
import com.jusfoun.hookah.core.domain.WXUserRecommend;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.WXUserRecommendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by admin on 2017/9/25.
 */
@Service
public class WXUserRecommendServiceImpl extends GenericServiceImpl<WXUserRecommend, String> implements WXUserRecommendService {

    @Resource
    private WXUserRecommendMapper wxUserRecommendMapper;

    @Override
    public HashMap<String, String> countInviteeAndReward(String userId) {
        return wxUserRecommendMapper.countInviteeAndReward(userId);
    }
}
