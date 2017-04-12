package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author xuym
 *
 */
public class PayCore extends GenericModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4068516825895770322L;

	/**进账*/
	public static final boolean INCOME = true;
	/**出账*/
	public static final boolean OUTCOME = false;
	
	public enum PayStatus{
		unpay(0),	//未付款
//		paying(1),	//支付中
		success(2),	//成功
		failure(3),	//失败
//		closed(4),	//关闭
//		unreceive(5),	//未收款
		balanceLow(6);	//余额不足
		private int value;
		private PayStatus(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String userId;
	private Date payDate;
	private String orderId;

	/** 订单编号*/

	private String orderSn;
	/** 支付方式代码*/
	private String payMode;
	/** 手续费*/
	private BigDecimal fee;
	/** 支付金额*/
	private BigDecimal amount;
	/** 支付状态*/
	private Integer payStatus;
	/** 交易号*/
	private String tradeNo;
	/** 收入:true；支出:false*/
	private Boolean incomeFlag;
	/** 对账成功*/
	private Boolean haveChecked;
	
	public PayCore(){}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public PayCore setOrderSn(String orderSn) {
		this.orderSn = orderSn;
		return this;
	}

	public String getPayMode() {
		return payMode;
	}

	public PayCore setPayMode(String payMode) {
		this.payMode = payMode;
		return this;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public PayCore setFee(BigDecimal fee) {
		this.fee = fee;
		return this;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public PayCore setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public PayCore setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public Date getPayDate() {
		return payDate;
	}

	public PayCore setPayDate(Date payDate) {
		this.payDate = payDate;
		return this;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public PayCore setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
		return this;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Boolean getIncomeFlag() {
		return incomeFlag;
	}

	public void setIncomeFlag(Boolean incomeFlag) {
		this.incomeFlag = incomeFlag;
	}

	public Boolean getHaveChecked() {
		return haveChecked;
	}

	public void setHaveChecked(Boolean haveChecked) {
		this.haveChecked = haveChecked;
	}

	/*@Override
	public String toString() {
		return "PayCore [id="+id
				+", userId="+userId
				+", payDate="+payDate
				+", orderId="+orderId
				+", orderSn="+orderSn
				+", amount="+amount
				+", payMode="+payMode
				+", fee="+fee
				+", payStatus="+payStatus
				+", tradeNo="+tradeNo
				+", incomeFlag="+incomeFlag
				+", haveChecked="+haveChecked
				+"]";
	}*/
	
}
