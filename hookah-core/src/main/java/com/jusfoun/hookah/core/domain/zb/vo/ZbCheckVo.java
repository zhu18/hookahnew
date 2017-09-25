package com.jusfoun.hookah.core.domain.zb.vo;

/**
 * 服务商审核VO
 */
public class ZbCheckVo {

    private String checkerId;       //  审核员ID
    private String autherId;        //  服务商ID
    private Integer checkStatus;     //  审核状态
    private String checkContent;    //  审核意见

    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    public String getAutherId() {
        return autherId;
    }

    public void setAutherId(String autherId) {
        this.autherId = autherId;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }
}
