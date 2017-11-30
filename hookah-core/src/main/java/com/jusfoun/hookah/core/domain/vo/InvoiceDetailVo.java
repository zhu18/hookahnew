package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.domain.mongo.MgCategoryAttrType;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import org.apache.ibatis.javassist.expr.Expr;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-3-17.
 */
public class InvoiceDetailVo extends Invoice implements Serializable {

    private String relationOrderSn;

    private UserInvoiceTitle userInvoiceTitle;

    private UserInvoiceAddress userInvoiceAddress;

    private ExpressInfo expressInfo;

    public String getRelationOrderSn() {
        return relationOrderSn;
    }

    public void setRelationOrderSn(String relationOrderSn) {
        this.relationOrderSn = relationOrderSn;
    }

    public UserInvoiceTitle getUserInvoiceTitle() {
        return userInvoiceTitle;
    }

    public void setUserInvoiceTitle(UserInvoiceTitle userInvoiceTitle) {
        this.userInvoiceTitle = userInvoiceTitle;
    }

    public UserInvoiceAddress getUserInvoiceAddress() {
        return userInvoiceAddress;
    }

    public void setUserInvoiceAddress(UserInvoiceAddress userInvoiceAddress) {
        this.userInvoiceAddress = userInvoiceAddress;
    }

    public ExpressInfo getExpressInfo() {
        return expressInfo;
    }

    public void setExpressInfo(ExpressInfo expressInfo) {
        this.expressInfo = expressInfo;
    }
}
