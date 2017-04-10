package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Cart;

public class CartVo extends Cart {
    private Byte isOnSale;

    private Byte checkStatus;

    public Byte getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Byte isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Byte getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Byte checkStatus) {
        this.checkStatus = checkStatus;
    }
}