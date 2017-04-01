package com.jusfoun.hookah.core.domain;
import com.jusfoun.hookah.core.generic.GenericModel;

public class AccNoToken extends GenericModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6771329225190859509L;
	private String userId;
	private String orderSn;
	private String accNo;
	private String token;
	private Integer status;//0无效，1有效

	public AccNoToken() {
	}

	public AccNoToken(String userId, String orderSn, String accNo, String token){
		this.userId = userId;
		this.orderSn = orderSn;
		this.accNo = accNo;
		this.token = token;
	}
	public AccNoToken(String userId, String orderSn, String accNo){
		this.userId = userId;
		this.orderSn = orderSn;
		this.accNo = accNo;
	}

	public AccNoToken(String userId, String accNo) {
		this.userId = userId;
		this.accNo = accNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}