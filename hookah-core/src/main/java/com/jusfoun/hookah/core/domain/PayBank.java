package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author zhaoshuai
 * Created by computer on 2017/7/19.
 */
public class PayBank extends GenericModel {

    @org.springframework.data.annotation.Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bankName;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getBankName() {return bankName;}

    public void setBankName(String bankName) {this.bankName = bankName;}
}
