package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.MessageTemplate;

/**
 * Created by ctp on 2017/7/13.
 */
public class MessageTemplateVo extends MessageTemplate{

    private String eventTypeName;//事件类型描述

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }
}
