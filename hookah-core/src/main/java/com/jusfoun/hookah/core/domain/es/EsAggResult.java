package com.jusfoun.hookah.core.domain.es;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-4-10.
 */
public class EsAggResult implements Serializable {
    private String id;
    private Long cnt;

    public EsAggResult(String id, Long cnt) {
        this.id = id;
        this.cnt = cnt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }
}
