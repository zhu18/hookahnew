package com.jusfoun.hookah.core.domain;

import java.io.Serializable;

/**
 * 信息编码
 * Created by wangjl on 2017-7-11.
 */
public class MessageCode implements Serializable {
    private Integer code;//code传值参考 hookah-core模块下的HookahConstants.java里的MESSAGE_XXX，例如MESSAGE_201
    private String businessId;//业务id
    /********************特殊需求字段*************************/
    //手机注册时需要
    private String mobileVerfCode;//手机验证码
    private String mobileNo;//手机号

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getMobileVerfCode() {
        return mobileVerfCode;
    }

    public void setMobileVerfCode(String mobileVerfCode) {
        this.mobileVerfCode = mobileVerfCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
