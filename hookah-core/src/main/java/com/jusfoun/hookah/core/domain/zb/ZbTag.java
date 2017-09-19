package com.jusfoun.hookah.core.domain.zb;

import com.jusfoun.hookah.core.generic.GenericModel;
import java.util.Date;

public class ZbTag extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_tag.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_tag.tag_content
     *
     * @mbggenerated
     */
    private String tagContent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_tag.type
     *
     * @mbggenerated
     */
    private Short type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_tag.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_tag.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_tag.add_operator
     *
     * @mbggenerated
     */
    private String addOperator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_tag.update_operator
     *
     * @mbggenerated
     */
    private String updateOperator;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_tag.id
     *
     * @return the value of zb_tag.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_tag.id
     *
     * @param id the value for zb_tag.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_tag.tag_content
     *
     * @return the value of zb_tag.tag_content
     *
     * @mbggenerated
     */
    public String getTagContent() {
        return tagContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_tag.tag_content
     *
     * @param tagContent the value for zb_tag.tag_content
     *
     * @mbggenerated
     */
    public void setTagContent(String tagContent) {
        this.tagContent = tagContent == null ? null : tagContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_tag.type
     *
     * @return the value of zb_tag.type
     *
     * @mbggenerated
     */
    public Short getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_tag.type
     *
     * @param type the value for zb_tag.type
     *
     * @mbggenerated
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_tag.add_time
     *
     * @return the value of zb_tag.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_tag.add_time
     *
     * @param addTime the value for zb_tag.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_tag.update_time
     *
     * @return the value of zb_tag.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_tag.update_time
     *
     * @param updateTime the value for zb_tag.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_tag.add_operator
     *
     * @return the value of zb_tag.add_operator
     *
     * @mbggenerated
     */
    public String getAddOperator() {
        return addOperator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_tag.add_operator
     *
     * @param addOperator the value for zb_tag.add_operator
     *
     * @mbggenerated
     */
    public void setAddOperator(String addOperator) {
        this.addOperator = addOperator == null ? null : addOperator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_tag.update_operator
     *
     * @return the value of zb_tag.update_operator
     *
     * @mbggenerated
     */
    public String getUpdateOperator() {
        return updateOperator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_tag.update_operator
     *
     * @param updateOperator the value for zb_tag.update_operator
     *
     * @mbggenerated
     */
    public void setUpdateOperator(String updateOperator) {
        this.updateOperator = updateOperator == null ? null : updateOperator.trim();
    }
}