package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.generic.GenericModel;

import java.io.Serializable;

/**
 * Created by lt on 2017/12/20.
 */
public class TransactionAnalysisVo extends GenericModel{

    private String dataSource;

    private Integer allOrderNum;

    private Long payedMoney;

    private Integer unPayedOrderNum;

    private Integer payedOrderNum;

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getAllOrderNum() {
        return allOrderNum;
    }

    public void setAllOrderNum(Integer allOrderNum) {
        this.allOrderNum = allOrderNum;
    }

    public Long getPayedMoney() {
        return payedMoney;
    }

    public void setPayedMoney(Long payedMoney) {
        this.payedMoney = payedMoney;
    }

    public Integer getUnPayedOrderNum() {
        return unPayedOrderNum;
    }

    public void setUnPayedOrderNum(Integer unPayedOrderNum) {
        this.unPayedOrderNum = unPayedOrderNum;
    }

    public Integer getPayedOrderNum() {
        return payedOrderNum;
    }

    public void setPayedOrderNum(Integer payedOrderNum) {
        this.payedOrderNum = payedOrderNum;
    }
}
