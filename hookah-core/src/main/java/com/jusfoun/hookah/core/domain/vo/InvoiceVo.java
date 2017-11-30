package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.ExpressInfo;
import com.jusfoun.hookah.core.domain.Invoice;
import com.jusfoun.hookah.core.domain.UserInvoiceAddress;
import com.jusfoun.hookah.core.domain.UserInvoiceTitle;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-3-17.
 */
public class InvoiceVo extends Invoice implements Serializable {

    private String userName;

    private String realName;

    private Byte userType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }
}
