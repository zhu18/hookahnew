package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import java.util.Date;

public class OrderAction extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_action.action_id
     *
     * @mbggenerated
     */
    @Id
    private String actionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_action.order_id
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_action.action_user
     *
     * @mbggenerated
     */
    private String actionUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_action.order_status
     *
     * @mbggenerated
     */
    private Integer orderStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_action.shipping_status
     *
     * @mbggenerated
     */
    private Integer shippingStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_action.pay_status
     *
     * @mbggenerated
     */
    private Integer payStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_action.action_note
     *
     * @mbggenerated
     */
    private String actionNote;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_action.log_time
     *
     * @mbggenerated
     */
    private Date logTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_action.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_action.version
     *
     * @mbggenerated
     */
    private Integer version;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_action.action_id
     *
     * @return the value of order_action.action_id
     *
     * @mbggenerated
     */
    public String getActionId() {
        return actionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_action.action_id
     *
     * @param actionId the value for order_action.action_id
     *
     * @mbggenerated
     */
    public void setActionId(String actionId) {
        this.actionId = actionId == null ? null : actionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_action.order_id
     *
     * @return the value of order_action.order_id
     *
     * @mbggenerated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_action.order_id
     *
     * @param orderId the value for order_action.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_action.action_user
     *
     * @return the value of order_action.action_user
     *
     * @mbggenerated
     */
    public String getActionUser() {
        return actionUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_action.action_user
     *
     * @param actionUser the value for order_action.action_user
     *
     * @mbggenerated
     */
    public void setActionUser(String actionUser) {
        this.actionUser = actionUser == null ? null : actionUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_action.order_status
     *
     * @return the value of order_action.order_status
     *
     * @mbggenerated
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_action.order_status
     *
     * @param orderStatus the value for order_action.order_status
     *
     * @mbggenerated
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_action.shipping_status
     *
     * @return the value of order_action.shipping_status
     *
     * @mbggenerated
     */
    public Integer getShippingStatus() {
        return shippingStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_action.shipping_status
     *
     * @param shippingStatus the value for order_action.shipping_status
     *
     * @mbggenerated
     */
    public void setShippingStatus(Integer shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_action.pay_status
     *
     * @return the value of order_action.pay_status
     *
     * @mbggenerated
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_action.pay_status
     *
     * @param payStatus the value for order_action.pay_status
     *
     * @mbggenerated
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_action.action_note
     *
     * @return the value of order_action.action_note
     *
     * @mbggenerated
     */
    public String getActionNote() {
        return actionNote;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_action.action_note
     *
     * @param actionNote the value for order_action.action_note
     *
     * @mbggenerated
     */
    public void setActionNote(String actionNote) {
        this.actionNote = actionNote == null ? null : actionNote.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_action.log_time
     *
     * @return the value of order_action.log_time
     *
     * @mbggenerated
     */
    public Date getLogTime() {
        return logTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_action.log_time
     *
     * @param logTime the value for order_action.log_time
     *
     * @mbggenerated
     */
    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_action.add_time
     *
     * @return the value of order_action.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_action.add_time
     *
     * @param addTime the value for order_action.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_action.version
     *
     * @return the value of order_action.version
     *
     * @mbggenerated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_action.version
     *
     * @param version the value for order_action.version
     *
     * @mbggenerated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}