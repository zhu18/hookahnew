package com.jusfoun.hookah.core.domain.es;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

/**
 * Created by wangjl on 2017-3-28.
 */
public class EsMapping {
    @JSONField(name="_all")
	private EsAllMapping _all;
	private Map<String,EsFieldMapping> properties;
	
	public EsMapping() {}

	public EsMapping(EsAllMapping _all, Map<String, EsFieldMapping> properties) {
		super();
		this._all = _all;
		this.properties = properties;
	}


	public EsAllMapping get_all() {
		return _all;
	}

	public void set_all(EsAllMapping _all) {
		this._all = _all;
	}

	public Map<String, EsFieldMapping> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, EsFieldMapping> properties) {
		this.properties = properties;
	}

	
}	
