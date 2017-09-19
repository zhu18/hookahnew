package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.domain.zb.ZbOrg;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface ZbOrgMapper extends GenericDao<ZbOrg> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_org
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_org
     *
     * @mbggenerated
     */
    int insert(ZbOrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_org
     *
     * @mbggenerated
     */
    int insertSelective(ZbOrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_org
     *
     * @mbggenerated
     */
    ZbOrg selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_org
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ZbOrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_org
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(ZbOrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_org
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ZbOrg record);
}