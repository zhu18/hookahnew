package com.jusfoun.hookah.core.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5714126201157423755L;



	private Integer payId;           //payMode 	支付方式（必填）
	                                 //
	private String orderSn;          //orderSn		订单号（必填）
	                                 //
	private String orderTitle;       //orderTitle	商品名称/商品的标题/交易标题/订单标题/订单关键
	                                 //
	private BigDecimal totalFee;     //totalFee	订单的资金，精度（必填）
	                                 //
	private String orderInfo;        //orderInfo	描述信息
	private String userId;

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getOrderTitle() {
		return orderTitle;
	}
	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
