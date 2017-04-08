package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Shop extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.shop_id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String shopId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shop.shop_name
     *
     * @mbggenerated
     */
    private String shopName;

    private String shopDesc;

    private String orgId;

    private Date addTime;

    private Integer version;

    private Integer isLocked;

    private Integer status;

    public Integer getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Integer isLocked) {
        this.isLocked = isLocked;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.shop_id
     *
     * @return the value of shop.shop_id
     * @mbggenerated
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.shop_id
     *
     * @param shopId the value for shop.shop_id
     * @mbggenerated
     */
    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shop.shop_name
     *
     * @return the value of shop.shop_name
     * @mbggenerated
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shop.shop_name
     *
     * @param shopName the value for shop.shop_name
     * @mbggenerated
     */
    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }
}