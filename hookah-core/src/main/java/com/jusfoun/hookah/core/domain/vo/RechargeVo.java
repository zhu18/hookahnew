package com.jusfoun.hookah.core.domain.vo;


import com.jusfoun.hookah.core.domain.PayCore;

public class RechargeVo extends PayCore {

	private static final long serialVersionUID = -850404099162086383L;
	private String user;
	private String startTime;
	private String endTime;
	private String startMoney;
	private String endMoney;

	public RechargeVo(String user, String startTime, String endTime, String startMoney, String endMoney) {
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startMoney = startMoney;
		this.endMoney = endMoney;
	}
	public RechargeVo() {
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartMoney() {
		return startMoney;
	}
	public void setStartMoney(String startMoney) {
		this.startMoney = startMoney;
	}
	public String getEndMoney() {
		return endMoney;
	}
	public void setEndMoney(String endMoney) {
		this.endMoney = endMoney;
	}
}
