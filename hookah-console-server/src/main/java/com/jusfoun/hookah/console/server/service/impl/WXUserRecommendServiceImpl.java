package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.WXUserRecommendMapper;
import com.jusfoun.hookah.core.domain.WXUserRecommend;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendVo;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.WXUserRecommendService;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/9/25.
 */
@Service
public class WXUserRecommendServiceImpl extends GenericServiceImpl<WXUserRecommend, String> implements WXUserRecommendService {

    @Resource
    private WXUserRecommendMapper wxUserRecommendMapper;

    @Override
    public HashMap<String, Integer> countInviteeAndReward(String userId) {
        return wxUserRecommendMapper.countInviteeAndReward(userId);
    }

    @Override
    public Pagination<WXUserRecommendVo> findRecommendListByCondition(HashMap<String, Object> paramMap) {
        Pagination<WXUserRecommendVo> pagination = new Pagination<>();
        //int startIndex = ((int)paramMap.get("pageNum") - 1) * (int)paramMap.get("pageSize");
        int count = wxUserRecommendMapper.countRecommendListByCondition(paramMap);
        List<WXUserRecommendVo> list = wxUserRecommendMapper.findRecommendListByCondition(paramMap);
        pagination.setTotalItems(count);
        pagination.setPageSize((int)paramMap.get("pageSize"));
        pagination.setCurrentPage((int)paramMap.get("pageNum"));
        pagination.setList(list);
        return pagination;
    }
}
