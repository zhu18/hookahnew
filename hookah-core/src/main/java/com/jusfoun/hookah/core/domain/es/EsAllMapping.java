package com.jusfoun.hookah.core.domain.es;

import java.io.Serializable;

/**
 * ES的映射中all需要的属性设置
 * @author WXF
 * 2016年7月10日
 */
public class EsAllMapping implements Serializable{
	private static final long serialVersionUID = 8660164235219268428L;
	private String analyzer;

	public EsAllMapping() {}
	public EsAllMapping(String analyzer) {
		super();
		this.analyzer = analyzer;
	}


	public String getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(String analyzer) {
		this.analyzer = analyzer;
	}
	
	//后面待补
	
	
	
}
