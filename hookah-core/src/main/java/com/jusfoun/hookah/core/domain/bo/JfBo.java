package com.jusfoun.hookah.core.domain.bo;

import java.io.Serializable;

/**
 * 消息传输 bo
 * @author dengxu
 */
public class JfBo implements Serializable {

    private String userId;

    private Integer sourceId;

    private String notes;

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public JfBo(String userId, Integer sourceId, String notes) {
        this.userId = userId;
        this.sourceId = sourceId;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "JfBo{" +
                "userId='" + userId + '\'' +
                ", sourceId=" + sourceId +
                ", notes='" + notes + '\'' +
                '}';
    }
}
