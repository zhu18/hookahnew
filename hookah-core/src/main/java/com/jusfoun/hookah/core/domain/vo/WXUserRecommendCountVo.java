package com.jusfoun.hookah.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import java.util.Date;

/**
 * Created by ndf on 2017/9/25.
 * 扩展实体类，用于展示 后台邀请好友查询
 */
public class WXUserRecommendCountVo extends GenericModel {

    private Integer inviteeNum;

    private Long rewardMoney;

    private Integer rewardJf;

    public Integer getInviteeNum() {
        return inviteeNum;
    }

    public void setInviteeNum(Integer inviteeNum) {
        this.inviteeNum = inviteeNum;
    }

    public Long getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(Long rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public Integer getRewardJf() {
        return rewardJf;
    }

    public void setRewardJf(Integer rewardJf) {
        this.rewardJf = rewardJf;
    }
}
