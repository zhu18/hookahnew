package com.jusfoun.hookah.core.domain;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class ApiParameter implements Serializable {
    private static final long serialVersionUID = 5579433505745880591L;
    @Id
    private Integer id;
    /**
     * API的信息id
     */
    private Integer apiId;
    /**
     * 参数名称
     */
    private String apiParaName;
    /**
     * 参数类型(1 常量 (11 整型 12 字符串)
     ===下面的字段为备用===
     2 变量 3 集合 4 虚拟集合 5 列表)
     */
    private Integer apiParaType;
    /**
     * 参数值(拨测用)
     */
    private String apiParaVal;
    /**
     * 参数是否是必须的1 必填
     */
    private Integer isMust;

    private Integer isPaging;
    /**
     * 参数字段类型(1 请求参数  2 响应参数)
     */
    private Integer fieldType;

    private String operator;

    private String desc;

    private Date createTime;

    private Date updateTime;

    private Integer cretateUser;

    private Integer updateUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public String getApiParaName() {
        return apiParaName;
    }

    public void setApiParaName(String apiParaName) {
        this.apiParaName = apiParaName == null ? null : apiParaName.trim();
    }

    public Integer getApiParaType() {
        return apiParaType;
    }

    public void setApiParaType(Integer apiParaType) {
        this.apiParaType = apiParaType;
    }

    public String getApiParaVal() {
        return apiParaVal;
    }

    public void setApiParaVal(String apiParaVal) {
        this.apiParaVal = apiParaVal == null ? null : apiParaVal.trim();
    }

    public Integer getIsMust() {
        return isMust;
    }

    public void setIsMust(Integer isMust) {
        this.isMust = isMust;
    }

    public Integer getIsPaging() {
        return isPaging;
    }

    public void setIsPaging(Integer isPaging) {
        this.isPaging = isPaging;
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCretateUser() {
        return cretateUser;
    }

    public void setCretateUser(Integer cretateUser) {
        this.cretateUser = cretateUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}