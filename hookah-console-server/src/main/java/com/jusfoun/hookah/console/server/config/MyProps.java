package com.jusfoun.hookah.console.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By
 * Copyright:  Copyright(C) 2016-2020
 * Company:    ronwo.com LTD.
 *
 * @version V1.0
 *          Createdate: 2016/11/13 上午1:02
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          2016/11/13     huanglei         1.0             1.0
 *          Why & What is modified: <修改原因描述>
 * @author: huanglei
 */
@Component
@ConfigurationProperties(prefix = "myconf")
public class MyProps {

    private Map<String, String> beetl = new HashMap<>();

    private Map<String, String> host = new HashMap<>();

    private Map<String, String> api = new HashMap<>();

    private String operateMobileNo;

    public Map<String, String> getBeetl() {
        return beetl;
    }

    public void setBeetl(Map<String, String> beetl) {
        this.beetl = beetl;
    }

    public Map<String, String> getHost() {
        return host;
    }

    public void setHost(Map<String, String> host) {
        this.host = host;
    }

    public Map<String, String> getApi() {
        return api;
    }

    public void setApi(Map<String, String> api) {
        this.api = api;
    }

    public String getOperateMobileNo() {
        return operateMobileNo;
    }

    public void setOperateMobileNo(String operateMobileNo) {
        this.operateMobileNo = operateMobileNo;
    }
}
