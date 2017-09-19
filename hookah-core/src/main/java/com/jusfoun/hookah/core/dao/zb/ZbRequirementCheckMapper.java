package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.domain.zb.ZbRequirementCheck;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface ZbRequirementCheckMapper extends GenericDao<ZbRequirementCheck> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_check
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_check
     *
     * @mbggenerated
     */
    int insert(ZbRequirementCheck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_check
     *
     * @mbggenerated
     */
    int insertSelective(ZbRequirementCheck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_check
     *
     * @mbggenerated
     */
    ZbRequirementCheck selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_check
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ZbRequirementCheck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_check
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ZbRequirementCheck record);
}