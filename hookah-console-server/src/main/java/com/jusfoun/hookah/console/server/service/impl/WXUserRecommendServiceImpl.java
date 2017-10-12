package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.WXUserRecommendMapper;
import com.jusfoun.hookah.core.domain.WxUserRecommend;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.WXUserRecommendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/9/25.
 */
@Service
public class WXUserRecommendServiceImpl extends GenericServiceImpl<WxUserRecommend, String> implements WXUserRecommendService {

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
        List<WXUserRecommendVo> list = wxUserRecommendMapper.getRecommendList(paramMap);
        int count = wxUserRecommendMapper.countRecommendList(paramMap);
        pagination.setTotalItems(count);
        pagination.setPageSize((int)paramMap.get("pageSize"));
        pagination.setCurrentPage((int)paramMap.get("pageNum"));
        pagination.setList(list);
        return pagination;
    }

    @Override
    public void updateWXUserRecommendIsAuthenticate(String inviteeId) {
        WxUserRecommend recommend =new WxUserRecommend();
        recommend.setInviteeid(inviteeId);
        recommend.setIsauthenticate((byte)1);  //将被邀请人是否认证的状态更改为是
        recommend.setUpdateTime(new Date());
        wxUserRecommendMapper.updateByInviteeidSelective(recommend);
    }

    @Override
    public void updateWXUserRecommendIsDeal(String inviteeId) {
        WxUserRecommend recommend=wxUserRecommendMapper.selectByInviteeId(inviteeId);
        if (recommend.getIsdeal()==0){
            recommend.setIsdeal((byte)1);  //将被邀请人是否成功交易的状态更改为是
            recommend.setUpdateTime(new Date());
            //wxUserRecommendMapper.updateByPrimaryKeySelective(recommend);
            wxUserRecommendMapper.updateByInviteeidSelective(recommend);
        }
    }

}
