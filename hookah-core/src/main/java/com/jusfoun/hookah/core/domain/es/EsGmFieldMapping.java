package com.jusfoun.hookah.core.domain.es;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-3-28.
 */
public class EsGmFieldMapping extends EsFieldMapping implements Serializable{

	private static final long serialVersionUID = 945443934281913161L;
	/*not_analyzed*/
	private String index;
	private String term_vector;

	private String format;

	/*是否包含在 all_source中*/
	private Boolean include_in_all;
	/*是否存储*/
	private Boolean store;

	private String[] copy_to;

	private Boolean fielddata;

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

    public EsGmFieldMapping(String index, String type, String analyzer, String term_vector, Boolean store, String search_analyzer) {
        super(type, analyzer, search_analyzer);
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
	public Boolean isStore() {
		return store;
	}
	public void setStore(Boolean store) {
		this.store = store;
	}

	public String getTerm_vector() {
		return term_vector;
	}

	public void setTerm_vector(String term_vector) {
		this.term_vector = term_vector;
	}

	public Boolean isInclude_in_all() {
		return include_in_all;
	}

	public void setInclude_in_all(Boolean include_in_all) {
		this.include_in_all = include_in_all;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String[] getCopy_to() {
		return copy_to;
	}

	public void setCopy_to(String[] copy_to) {
		this.copy_to = copy_to;
	}

	public Boolean getFielddata() {
		return fielddata;
	}

	public void setFielddata(Boolean fielddata) {
		this.fielddata = fielddata;
	}
}
