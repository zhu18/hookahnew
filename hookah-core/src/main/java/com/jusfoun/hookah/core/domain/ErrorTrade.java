package com.jusfoun.hookah.core.domain;

import java.io.Serializable;
import java.util.Date;


public class ErrorTrade implements Serializable,Comparable<ErrorTrade>{
	private static final long serialVersionUID = 3286568732822242862L;
	public enum ErrorType{
		s_dupli(1),//商城重复
		tp_dupli(2),//第三方支付重复
		more(3),//长账
		less(4),//短账
		diff(5);//差额
		private int value;
		private ErrorType(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	}
	private String orderSn;
	private Integer errorType;
	private String shopMoney;
	private String thirdPartyMoney;
	/**收入:1；支出:0*/
	private Boolean incomeFlag;
	private Date checkDate;
	public ErrorTrade() {
	}
	public ErrorTrade(String orderSn, Integer errorType, String shopMoney,
			String thirdPartyMoney, Boolean incomeFlag, Date checkDate) {
		this.orderSn = orderSn;
		this.errorType = errorType;
		this.shopMoney = shopMoney;
		this.thirdPartyMoney = thirdPartyMoney;
		this.incomeFlag = incomeFlag;
		this.checkDate = checkDate;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Integer getErrorType() {
		return errorType;
	}
	public void setErrorType(Integer errorType) {
		this.errorType = errorType;
	}
	public String getShopMoney() {
		return shopMoney;
	}
	public void setShopMoney(String shopMoney) {
		this.shopMoney = shopMoney;
	}
	public String getThirdPartyMoney() {
		return thirdPartyMoney;
	}
	public void setThirdPartyMoney(String thirdPartyMoney) {
		this.thirdPartyMoney = thirdPartyMoney;
	}
	public Boolean getIncomeFlag() {
		return incomeFlag;
	}
	public void setIncomeFlag(Boolean incomeFlag) {
		this.incomeFlag = incomeFlag;
	}
	public Date getErrorDate() {
		return checkDate;
	}

	public void setErrorDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Override
	public int compareTo(ErrorTrade o) {
		return this.orderSn.compareTo(o.getOrderSn());
	}
}
