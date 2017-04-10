package com.jusfoun.hookah.core.domain.es;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-4-10.
 */
public class EsAgg implements Serializable {
    private String aggName;
    private String fieldName;

    public EsAgg(String aggName, String fieldName) {
        this.aggName = aggName;
        this.fieldName = fieldName;
    }

    public String getAggName() {
        return aggName;
    }

    public void setAggName(String aggName) {
        this.aggName = aggName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
