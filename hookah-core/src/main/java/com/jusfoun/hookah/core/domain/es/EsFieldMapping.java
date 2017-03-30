package com.jusfoun.hookah.core.domain.es;

import java.io.Serializable;

/**
 * 属性构建的实体类，后面考虑部分属性使用枚举进行可供选择
 * 构建基类，suggest类进行作为子类，后面考虑将其中的一些Boolean字段进行也做特殊构建
 * @author WXF
 * 2016年7月10日
 */
public class EsFieldMapping implements Serializable{

	private static final long serialVersionUID = -6261048300857335092L;

	/*数据类型 text keyword long*/
	private String type;

	private String analyzer;

	private String search_analyzer;


	public EsFieldMapping() {}

	public EsFieldMapping(String type) {
		super();
		this.type = type;
	}

	public EsFieldMapping(String type, String analyzer) {
		super();
		this.type = type;
		this.analyzer = analyzer;
	}

    public EsFieldMapping(String type, String analyzer, String search_analyzer) {
        super();
        this.type = type;
        this.analyzer = analyzer;
        this.search_analyzer = search_analyzer;
    }

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAnalyzer() {
		return analyzer;
	}
	public void setAnalyzer(String analyzer) {
		this.analyzer = analyzer;
	}

	public String getSearch_analyzer() {
		return search_analyzer;
	}

	public void setSearch_analyzer(String search_analyzer) {
		this.search_analyzer = search_analyzer;
	}

	
	
}
