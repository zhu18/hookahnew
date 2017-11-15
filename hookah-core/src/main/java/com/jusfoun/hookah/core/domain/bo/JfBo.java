package com.jusfoun.hookah.core.domain.bo;

/**
 * 消息传输 bo
 */
public class JfBo {

    private String userId;

    private Integer sourceId;

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

    @Override
    public String toString() {
        return "JfBo{" +
                "userId='" + userId + '\'' +
                ", sourceId=" + sourceId +
                '}';
    }
}
