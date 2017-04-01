package com.jusfoun.hookah.core.domain.vo;


import com.jusfoun.hookah.core.domain.PayCore;

public class PayCoreVo extends PayCore {

	private static final long serialVersionUID = 4746140460248961948L;
	private String startTime;
	private String endTime;
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
}
