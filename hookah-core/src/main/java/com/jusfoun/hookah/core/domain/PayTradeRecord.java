package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

public class PayTradeRecord extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_trade_record.id
     *
     * @mbggenerated
     */
    @Id
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_trade_record.pay_account_id
     *
     * @mbggenerated
     */
    private Long payAccountId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_trade_record.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_trade_record.money
     *
     * @mbggenerated
     */
    private Long money;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_trade_record.trade_type
     *
     * @mbggenerated
     */
    private Byte tradeType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_trade_record.trade_status
     *
     * @mbggenerated
     */
    private Byte tradeStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_trade_record.order_sn
     *
     * @mbggenerated
     */
    private String orderSn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_trade_record.add_time
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_trade_record.add_operator
     *
     * @mbggenerated
     */
    private String addOperator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_trade_record.update_time
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_trade_record.update_operator
     *
     * @mbggenerated
     */
    private String updateOperator;

    private String goodsSn; // 产品代码
    private Date transferDate; // 业务日期

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_trade_record.id
     *
     * @return the value of pay_trade_record.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_trade_record.id
     *
     * @param id the value for pay_trade_record.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_trade_record.pay_account_id
     *
     * @return the value of pay_trade_record.pay_account_id
     *
     * @mbggenerated
     */
    public Long getPayAccountId() {
        return payAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_trade_record.pay_account_id
     *
     * @param payAccountId the value for pay_trade_record.pay_account_id
     *
     * @mbggenerated
     */
    public void setPayAccountId(Long payAccountId) {
        this.payAccountId = payAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_trade_record.user_id
     *
     * @return the value of pay_trade_record.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_trade_record.user_id
     *
     * @param userId the value for pay_trade_record.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_trade_record.money
     *
     * @return the value of pay_trade_record.money
     *
     * @mbggenerated
     */
    public Long getMoney() {
        return money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_trade_record.money
     *
     * @param money the value for pay_trade_record.money
     *
     * @mbggenerated
     */
    public void setMoney(Long money) {
        this.money = money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_trade_record.trade_type
     *
     * @return the value of pay_trade_record.trade_type
     *
     * @mbggenerated
     */
    public Byte getTradeType() {
        return tradeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_trade_record.trade_type
     *
     * @param tradeType the value for pay_trade_record.trade_type
     *
     * @mbggenerated
     */
    public void setTradeType(Byte tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_trade_record.trade_status
     *
     * @return the value of pay_trade_record.trade_status
     *
     * @mbggenerated
     */
    public Byte getTradeStatus() {
        return tradeStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_trade_record.trade_status
     *
     * @param tradeStatus the value for pay_trade_record.trade_status
     *
     * @mbggenerated
     */
    public void setTradeStatus(Byte tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_trade_record.order_sn
     *
     * @return the value of pay_trade_record.order_sn
     *
     * @mbggenerated
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_trade_record.order_sn
     *
     * @param orderSn the value for pay_trade_record.order_sn
     *
     * @mbggenerated
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_trade_record.add_time
     *
     * @return the value of pay_trade_record.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_trade_record.add_time
     *
     * @param addTime the value for pay_trade_record.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_trade_record.add_operator
     *
     * @return the value of pay_trade_record.add_operator
     *
     * @mbggenerated
     */
    public String getAddOperator() {
        return addOperator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_trade_record.add_operator
     *
     * @param addOperator the value for pay_trade_record.add_operator
     *
     * @mbggenerated
     */
    public void setAddOperator(String addOperator) {
        this.addOperator = addOperator == null ? null : addOperator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_trade_record.update_time
     *
     * @return the value of pay_trade_record.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_trade_record.update_time
     *
     * @param updateTime the value for pay_trade_record.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_trade_record.update_operator
     *
     * @return the value of pay_trade_record.update_operator
     *
     * @mbggenerated
     */
    public String getUpdateOperator() {
        return updateOperator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_trade_record.update_operator
     *
     * @param updateOperator the value for pay_trade_record.update_operator
     *
     * @mbggenerated
     */
    public void setUpdateOperator(String updateOperator) {
        this.updateOperator = updateOperator == null ? null : updateOperator.trim();
    }
}