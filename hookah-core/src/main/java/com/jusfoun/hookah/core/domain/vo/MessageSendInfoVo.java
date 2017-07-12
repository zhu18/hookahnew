package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.MessageSendInfo;

/**
 * Created by ctp on 2017/7/12.
 */
public class MessageSendInfoVo extends MessageSendInfo {
    private String sendUserName;
    private String receiveUserName;
    private String eventTypeName;

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }
}
