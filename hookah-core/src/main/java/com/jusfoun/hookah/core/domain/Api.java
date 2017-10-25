package com.jusfoun.hookah.core.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Api implements Serializable {


    private static final long serialVersionUID = -5701810836570229755L;
    public final  Integer SUCCESS = 1;

    public final  Integer ERROR = 0;

    @Id
    private Integer id;
    /**
     * api名称
     */
    private String name;
    /**
     * aip编码
     */
    private String code;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 基础URL信息
     */
    private String sourceUrl;
    /**
     * 封装后的url
     */
    private String wrapperUrl;
    /**
     * 请求方式(1 get 2 post)
     */
    private Integer method;
    /**
     * 封装后的请求方式(1 get 2 post)
     */
    private Integer wrapperMethod;
    /**
     * 返回格式(1json 2xml)
     */
    private Integer returnFormat;
    /**
     * 封装返回格式(1json 2xml)
     */
    private Integer wrapperReturnFormat;
    /**
     * 是否包含分页参数（1是0否）
     */
    private Integer isPaging;
    /**
     * API的类型，1 rest  2 wsdl
     */
    private Integer apiType;
    /**
     * // 使用方式 1 自用 2 公开
     */
    private Integer useWay;
    /**
     * 供应商id
     */
    private Integer supplierId;
    /**
     * api文档名称
     */
    private String docName;
    /**
     * 文档来源（1url，2file）
     */
    private Integer docType;

    private String docUrl;
    /**
     * 文档状态（0删除 1草稿、2待测试、3测试通过、4测试未通过）
     */
    private Integer status;
    /**
     * 版本号
     */
    private String version;

    private Integer createUser;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 原始接口秘钥值
     */
    private String secretKey;
    /**
     * 原始接口秘钥名称
     */
    private String secretKeyName;
    /**
     * 原始接口状态编码属性
     */
    private String codeAttribute;
    /**
     * 原始接口编码属性成功
     */
    private String codeAttributeSuccess;
    /**
     * 原始接口编码属性失败
     */
    private String codeAttributeFail;
    /**
     * 原始接口编码属性成功无数据
     */
    private String codeAttributeNodata;



    /**
     * 用户信息

     */
    private User user;

    /**
     * API响应
     */
    private String apiPreServer;
    /**
     * toke的请求方式1 header 2 请求头 3 请求体
     */
    private Integer keyType;
    /**
     * 返回格式化json
     */
    private String retJsonFormat;

    /**
     * 响应参数
     */
    private List<ApiParameter> requestParameter ;



    /**
     * 响应参数
     */
    private List<ApiParameter> returnParameter ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl == null ? null : sourceUrl.trim();
    }

    public String getWrapperUrl() {
        return wrapperUrl;
    }

    public void setWrapperUrl(String wrapperUrl) {
        this.wrapperUrl = wrapperUrl == null ? null : wrapperUrl.trim();
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public Integer getWrapperMethod() {
        return wrapperMethod;
    }

    public void setWrapperMethod(Integer wrapperMethod) {
        this.wrapperMethod = wrapperMethod;
    }

    public Integer getReturnFormat() {
        return returnFormat;
    }

    public void setReturnFormat(Integer returnFormat) {
        this.returnFormat = returnFormat;
    }

    public Integer getWrapperReturnFormat() {
        return wrapperReturnFormat;
    }

    public void setWrapperReturnFormat(Integer wrapperReturnFormat) {
        this.wrapperReturnFormat = wrapperReturnFormat;
    }

    public Integer getIsPaging() {
        return isPaging;
    }

    public void setIsPaging(Integer isPaging) {
        this.isPaging = isPaging;
    }

    public Integer getApiType() {
        return apiType;
    }

    public void setApiType(Integer apiType) {
        this.apiType = apiType;
    }

    public Integer getUseWay() {
        return useWay;
    }

    public void setUseWay(Integer useWay) {
        this.useWay = useWay;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName == null ? null : docName.trim();
    }

    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl == null ? null : docUrl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey == null ? null : secretKey.trim();
    }

    public String getSecretKeyName() {
        return secretKeyName;
    }

    public void setSecretKeyName(String secretKeyName) {
        this.secretKeyName = secretKeyName == null ? null : secretKeyName.trim();
    }

    public String getCodeAttribute() {
        return codeAttribute;
    }

    public void setCodeAttribute(String codeAttribute) {
        this.codeAttribute = codeAttribute == null ? null : codeAttribute.trim();
    }

    public String getCodeAttributeSuccess() {
        return codeAttributeSuccess;
    }

    public void setCodeAttributeSuccess(String codeAttributeSuccess) {
        this.codeAttributeSuccess = codeAttributeSuccess == null ? null : codeAttributeSuccess.trim();
    }

    public String getCodeAttributeFail() {
        return codeAttributeFail;
    }

    public void setCodeAttributeFail(String codeAttributeFail) {
        this.codeAttributeFail = codeAttributeFail == null ? null : codeAttributeFail.trim();
    }

    public String getCodeAttributeNodata() {
        return codeAttributeNodata;
    }

    public void setCodeAttributeNodata(String codeAttributeNodata) {
        this.codeAttributeNodata = codeAttributeNodata == null ? null : codeAttributeNodata.trim();
    }

    public String getApiPreServer() {
        return apiPreServer;
    }

    public void setApiPreServer(String apiPreServer) {
        this.apiPreServer = apiPreServer;
    }

    public List<ApiParameter> getRequestParameter() {
        return requestParameter;
    }

    public void setRequestParameter(List<ApiParameter> requestParameter) {
        this.requestParameter = requestParameter;
    }

    public List<ApiParameter> getReturnParameter() {
        return returnParameter;
    }

    public void setReturnParameter(List<ApiParameter> returnParameter) {
        this.returnParameter = returnParameter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Integer getKeyType() {
        return keyType;
    }

    public void setKeyType(Integer keyType) {
        this.keyType = keyType;
    }

    public String getRetJsonFormat() {
        return retJsonFormat;
    }

    public void setRetJsonFormat(String retJsonFormat) {
        this.retJsonFormat = retJsonFormat;
    }
}