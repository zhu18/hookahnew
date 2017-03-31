package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.SysGroup;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface SysGroupMapper extends GenericDao<SysGroup> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_group
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_group
     *
     * @mbggenerated
     */
    int insert(SysGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_group
     *
     * @mbggenerated
     */
    int insertSelective(SysGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_group
     *
     * @mbggenerated
     */
    SysGroup selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_group
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_group
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysGroup record);
}