package com.jusfoun.hookah.core.domain.vo;

/**
 * Created by ctp on 2017/7/18.
 */
public class UserFundCritVo extends BaseCritVo {

    private String userName;
    private String orgName;
    private String phone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
