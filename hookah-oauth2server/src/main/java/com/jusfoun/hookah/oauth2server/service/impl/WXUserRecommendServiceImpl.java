package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.WXUserRecommendMapper;
import com.jusfoun.hookah.core.domain.WXUserRecommend;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.WXUserRecommendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by lt on 2017/9/27.
 */
@Service
public class WXUserRecommendServiceImpl extends GenericServiceImpl<WXUserRecommend, String> implements WXUserRecommendService {
    @Resource
    private WXUserRecommendMapper wxUserRecommendMapper;

    @Resource
    public void setDao(WXUserRecommendMapper wxUserRecommendMapper) {
        super.setDao(wxUserRecommendMapper);
    }

    @Override
    public HashMap<String, Integer> countInviteeAndReward(String userId) {
        return null;
    }

    @Override
    public Pagination<WXUserRecommendVo> findRecommendListByCondition(HashMap<String, Object> paramMap) {
        return null;
    }

    @Override
    public void updateWXUserRecommendIsAuthenticate(String inviteeId) {

    }

    @Override
    public void updateWXUserRecommendIsDeal(String inviteeId) {
    }
}
