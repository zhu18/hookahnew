package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.dao.GenericDao;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementFiles;

public interface ZbRequirementFilesMapper extends GenericDao<ZbRequirementFiles> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_files
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_files
     *
     * @mbggenerated
     */
    int insert(ZbRequirementFiles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_files
     *
     * @mbggenerated
     */
    int insertSelective(ZbRequirementFiles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_files
     *
     * @mbggenerated
     */
    ZbRequirementFiles selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_files
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ZbRequirementFiles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_requirement_files
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ZbRequirementFiles record);
}