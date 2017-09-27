package com.jusfoun.hookah.core.domain;

import java.io.Serializable;

/**
 * Created by ctp on 2017-8-29.
 */
public class ChannelTransData implements Serializable {
    private String transData;//传输数据(加密)
    private String checkCode;//校验码
    private Long timestamp;//时间
    private String localUrl;//本地域名

    public String getTransData() {
        return transData;
    }

    public void setTransData(String transData) {
        this.transData = transData;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public static class RelationData<T> {
        private String userId;
        private String pwd;
        private T data;
        private int opera;// 0 撤回；1 推送

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public int getOpera() {
            return opera;
        }

        public void setOpera(int opera) {
            this.opera = opera;
        }
    }
}

