package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Supplier;

/**
 * Created by lt on 2017/7/11.
 */
public class SupplierVo extends Supplier {

    private Integer userType;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}