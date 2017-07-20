package com.jusfoun.hookah.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.domain.Goods;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by dengxu on 2017/6/2/0002.
 */
public class GoodsCheckedVo extends Goods {

    private String checkUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    private String orgName;

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
