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

    private List<InvoiceOrder> invoiceOrderList;

    private UserInvoiceTitle userInvoiceTitle;

    private UserInvoiceAddress userInvoiceAddress;

    private ExpressInfo expressInfo;

    /**
     * 后台发票详情用
     */
    private List<OrderInfoInvoiceVo> orderInfoInvoiceVoList;

    private UserInvoiceVo userInvoiceVo;

    public String getRelationOrderSn() {
        return relationOrderSn;
    }

    public void setRelationOrderSn(String relationOrderSn) {
        this.relationOrderSn = relationOrderSn;
    }

    public List<InvoiceOrder> getInvoiceOrderList() {
        return invoiceOrderList;
    }

    public void setInvoiceOrderList(List<InvoiceOrder> invoiceOrderList) {
        this.invoiceOrderList = invoiceOrderList;
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

    public List<OrderInfoInvoiceVo> getOrderInfoInvoiceVoList() {
        return orderInfoInvoiceVoList;
    }

    public void setOrderInfoInvoiceVoList(List<OrderInfoInvoiceVo> orderInfoInvoiceVoList) {
        this.orderInfoInvoiceVoList = orderInfoInvoiceVoList;
    }

    public UserInvoiceVo getUserInvoiceVo() {
        return userInvoiceVo;
    }

    public void setUserInvoiceVo(UserInvoiceVo userInvoiceVo) {
        this.userInvoiceVo = userInvoiceVo;
    }

    public static class InvoiceOrder implements Serializable {
        private String orderSn; // 关联订单号
        private Long orderAmount; // 订单金额

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public Long getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(Long orderAmount) {
            this.orderAmount = orderAmount;
        }
    }
}
