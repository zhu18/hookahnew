package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.WXUserRecommend;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.HashMap;

public interface WXUserRecommendMapper extends GenericDao<WXUserRecommend> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_recommend
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_recommend
     *
     * @mbggenerated
     */
    int insert(WXUserRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_recommend
     *
     * @mbggenerated
     */
    int insertSelective(WXUserRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_recommend
     *
     * @mbggenerated
     */
    WXUserRecommend selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WXUserRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WXUserRecommend record);

    HashMap<String,Integer> countInviteeAndReward(String userId);
}