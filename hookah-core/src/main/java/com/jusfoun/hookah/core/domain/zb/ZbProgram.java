package com.jusfoun.hookah.core.domain.zb;

import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import java.util.Date;

public class ZbProgram extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_program.id
     *
     * @mbggenerated
     */
    @Id
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_program.apply_id
     *
     * @mbggenerated
     */
    private Long applyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_program.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_program.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_program.content
     *
     * @mbggenerated
     */
    private String content;

    private String checkAdvice;

    private Short status;

    private Long requirementId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_program.id
     *
     * @return the value of zb_program.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_program.id
     *
     * @param id the value for zb_program.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_program.apply_id
     *
     * @return the value of zb_program.apply_id
     *
     * @mbggenerated
     */
    public Long getApplyId() {
        return applyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_program.apply_id
     *
     * @param applyId the value for zb_program.apply_id
     *
     * @mbggenerated
     */
    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_program.title
     *
     * @return the value of zb_program.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_program.title
     *
     * @param title the value for zb_program.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_program.add_time
     *
     * @return the value of zb_program.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_program.add_time
     *
     * @param addTime the value for zb_program.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_program.content
     *
     * @return the value of zb_program.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_program.content
     *
     * @param content the value for zb_program.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCheckAdvice() {
        return checkAdvice;
    }

    public void setCheckAdvice(String checkAdvice) {
        this.checkAdvice = checkAdvice == null ? null : checkAdvice.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }
}