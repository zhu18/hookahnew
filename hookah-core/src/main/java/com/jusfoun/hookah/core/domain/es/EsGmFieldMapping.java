package com.jusfoun.hookah.core.domain.es;

import java.io.Serializable;

/**
 * 通用的字段的mapping，目前这里主要将Boolean字段进行移动到这儿
 * 后面考虑植入非suggest的属性字段
 * Gm General Model
 * @author WXF
 * 2016年7月10日
 */
public class EsGmFieldMapping extends EsFieldMapping implements Serializable{

	private static final long serialVersionUID = 945443934281913161L;
	/*not_analyzed*/
	private String index;
	private String term_vector;

	private String format;

	/*是否包含在 all_source中*/
	private boolean include_in_all;
	/*是否存储*/
	private boolean store;

	public EsGmFieldMapping() {}

	public EsGmFieldMapping(String index, String type) {
		super(type);
		this.index = index;
	}

	public EsGmFieldMapping(String index, String type, String analyzer, String term_vector) {
		super(type, analyzer);
		this.index = index;
		this.term_vector = term_vector;
	}

    public EsGmFieldMapping(String index, String type, String analyzer, String term_vector, boolean store) {
        super(type, analyzer);
        this.index = index;
        this.term_vector = term_vector;
        this.store = store;
    }

	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public boolean isStore() {
		return store;
	}
	public void setStore(boolean store) {
		this.store = store;
	}

	public String getTerm_vector() {
		return term_vector;
	}

	public void setTerm_vector(String term_vector) {
		this.term_vector = term_vector;
	}

	public boolean isInclude_in_all() {
		return include_in_all;
	}

	public void setInclude_in_all(boolean include_in_all) {
		this.include_in_all = include_in_all;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	

	
	
}
