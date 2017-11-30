package com.jusfoun.hookah.core.domain.bo;

import java.io.Serializable;

/**
 * 消息传输 bo
 */
public class JfBo implements Serializable {

    private String userId;

    private Integer sourceId;

    private String msg;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JfBo(String userId, Integer sourceId) {
        this.userId = userId;
        this.sourceId = sourceId;
    }

    @Override
    public String toString() {
        return "JfBo{" +
                "userId='" + userId + '\'' +
                ", sourceId=" + sourceId +
                ", msg='" + msg + '\'' +
                '}';
    }
}
