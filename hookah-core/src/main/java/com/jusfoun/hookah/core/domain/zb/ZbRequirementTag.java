package com.jusfoun.hookah.core.domain.zb;

import com.jusfoun.hookah.core.generic.GenericModel;
import java.util.Date;

public class ZbRequirementTag extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement_tag.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement_tag.requirement_id
     *
     * @mbggenerated
     */
    private Long requirementId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement_tag.tag_id
     *
     * @mbggenerated
     */
    private Integer tagId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement_tag.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement_tag.id
     *
     * @return the value of zb_requirement_tag.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement_tag.id
     *
     * @param id the value for zb_requirement_tag.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement_tag.requirement_id
     *
     * @return the value of zb_requirement_tag.requirement_id
     *
     * @mbggenerated
     */
    public Long getRequirementId() {
        return requirementId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement_tag.requirement_id
     *
     * @param requirementId the value for zb_requirement_tag.requirement_id
     *
     * @mbggenerated
     */
    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement_tag.tag_id
     *
     * @return the value of zb_requirement_tag.tag_id
     *
     * @mbggenerated
     */
    public Integer getTagId() {
        return tagId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement_tag.tag_id
     *
     * @param tagId the value for zb_requirement_tag.tag_id
     *
     * @mbggenerated
     */
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement_tag.add_time
     *
     * @return the value of zb_requirement_tag.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement_tag.add_time
     *
     * @param addTime the value for zb_requirement_tag.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}