package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.WXUserInfo;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface WXUserInfoMapper extends GenericDao<WXUserInfo> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_info
     *
     * @mbggenerated
     */
    int insert(WXUserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_info
     *
     * @mbggenerated
     */
    int insertSelective(WXUserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_info
     *
     * @mbggenerated
     */
    WXUserInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WXUserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WXUserInfo record);
}