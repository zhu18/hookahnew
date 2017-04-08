package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Goods extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.goods_id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String goodsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.goods_sn
     *
     * @mbggenerated
     */
    private String goodsSn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.domain_id
     *
     * @mbggenerated
     */
    private String domainId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.goods_name
     *
     * @mbggenerated
     */
    private String goodsName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.goods_brief
     *
     * @mbggenerated
     */
    private String goodsBrief;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.goods_desc
     *
     * @mbggenerated
     */
    private String goodsDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.keywords
     *
     * @mbggenerated
     */
    private String keywords;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.goods_img
     *
     * @mbggenerated
     */
    private String goodsImg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.is_delete
     *
     * @mbggenerated
     */
    private Byte isDelete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.goods_type
     *
     * @mbggenerated
     */
    private Byte goodsType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.goods_number
     *
     * @mbggenerated
     */
    private Integer goodsNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.warn_number
     *
     * @mbggenerated
     */
    private Integer warnNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.goods_area
     *
     * @mbggenerated
     */
    private String goodsArea;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.onsale_start_date
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date onsaleStartDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.onsale_end_date
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date onsaleEndDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.cat_id
     *
     * @mbggenerated
     */
    private String catId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.add_time
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.last_update_time
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.is_real
     *
     * @mbggenerated
     */
    private Integer isReal;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.shop_price
     *
     * @mbggenerated
     */
    private Float shopPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.shop_number
     *
     * @mbggenerated
     */
    private Integer shopNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.shop_format
     *
     * @mbggenerated
     */
    private Byte shopFormat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.seller_note
     *
     * @mbggenerated
     */
    private String sellerNote;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.add_user
     *
     * @mbggenerated
     */
    private String addUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.user_id
     *
     * @mbggenerated
     */
    private String userId;

    private String userName;

    private String shopId;

    private String shopName;


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.check_status
     *
     * @mbggenerated
     */
    private Byte checkStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.is_onsale
     *
     * @mbggenerated
     */
    private Byte isOnsale;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.is_update
     *
     * @mbggenerated
     */
    private Byte isUpdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods.version
     *
     * @mbggenerated
     */
    private Integer version;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.goods_id
     *
     * @return the value of goods.goods_id
     * @mbggenerated
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.goods_id
     *
     * @param goodsId the value for goods.goods_id
     * @mbggenerated
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.goods_sn
     *
     * @return the value of goods.goods_sn
     * @mbggenerated
     */
    public String getGoodsSn() {
        return goodsSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.goods_sn
     *
     * @param goodsSn the value for goods.goods_sn
     * @mbggenerated
     */
    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn == null ? null : goodsSn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.domain_id
     *
     * @return the value of goods.domain_id
     * @mbggenerated
     */
    public String getDomainId() {
        return domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.domain_id
     *
     * @param domainId the value for goods.domain_id
     * @mbggenerated
     */
    public void setDomainId(String domainId) {
        this.domainId = domainId == null ? null : domainId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.goods_name
     *
     * @return the value of goods.goods_name
     * @mbggenerated
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.goods_name
     *
     * @param goodsName the value for goods.goods_name
     * @mbggenerated
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.goods_brief
     *
     * @return the value of goods.goods_brief
     * @mbggenerated
     */
    public String getGoodsBrief() {
        return goodsBrief;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.goods_brief
     *
     * @param goodsBrief the value for goods.goods_brief
     * @mbggenerated
     */
    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief == null ? null : goodsBrief.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.goods_desc
     *
     * @return the value of goods.goods_desc
     * @mbggenerated
     */
    public String getGoodsDesc() {
        return goodsDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.goods_desc
     *
     * @param goodsDesc the value for goods.goods_desc
     * @mbggenerated
     */
    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc == null ? null : goodsDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.keywords
     *
     * @return the value of goods.keywords
     * @mbggenerated
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.keywords
     *
     * @param keywords the value for goods.keywords
     * @mbggenerated
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.goods_img
     *
     * @return the value of goods.goods_img
     * @mbggenerated
     */
    public String getGoodsImg() {
        return goodsImg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.goods_img
     *
     * @param goodsImg the value for goods.goods_img
     * @mbggenerated
     */
    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg == null ? null : goodsImg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.is_delete
     *
     * @return the value of goods.is_delete
     * @mbggenerated
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.is_delete
     *
     * @param isDelete the value for goods.is_delete
     * @mbggenerated
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.goods_type
     *
     * @return the value of goods.goods_type
     * @mbggenerated
     */
    public Byte getGoodsType() {
        return goodsType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.goods_type
     *
     * @param goodsType the value for goods.goods_type
     * @mbggenerated
     */
    public void setGoodsType(Byte goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.goods_number
     *
     * @return the value of goods.goods_number
     * @mbggenerated
     */
    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.goods_number
     *
     * @param goodsNumber the value for goods.goods_number
     * @mbggenerated
     */
    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.warn_number
     *
     * @return the value of goods.warn_number
     * @mbggenerated
     */
    public Integer getWarnNumber() {
        return warnNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.warn_number
     *
     * @param warnNumber the value for goods.warn_number
     * @mbggenerated
     */
    public void setWarnNumber(Integer warnNumber) {
        this.warnNumber = warnNumber;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.goods_area
     *
     * @return the value of goods.goods_area
     * @mbggenerated
     */
    public String getGoodsArea() {
        return goodsArea;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.goods_area
     *
     * @param goodsArea the value for goods.goods_area
     * @mbggenerated
     */
    public void setGoodsArea(String goodsArea) {
        this.goodsArea = goodsArea == null ? null : goodsArea.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.onsale_start_date
     *
     * @return the value of goods.onsale_start_date
     * @mbggenerated
     */
    public Date getOnsaleStartDate() {
        return onsaleStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.onsale_start_date
     *
     * @param onsaleStartDate the value for goods.onsale_start_date
     * @mbggenerated
     */
    public void setOnsaleStartDate(Date onsaleStartDate) {
        this.onsaleStartDate = onsaleStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.onsale_end_date
     *
     * @return the value of goods.onsale_end_date
     * @mbggenerated
     */
    public Date getOnsaleEndDate() {
        return onsaleEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.onsale_end_date
     *
     * @param onsaleEndDate the value for goods.onsale_end_date
     * @mbggenerated
     */
    public void setOnsaleEndDate(Date onsaleEndDate) {
        this.onsaleEndDate = onsaleEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.cat_id
     *
     * @return the value of goods.cat_id
     * @mbggenerated
     */
    public String getCatId() {
        return catId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.cat_id
     *
     * @param catId the value for goods.cat_id
     * @mbggenerated
     */
    public void setCatId(String catId) {
        this.catId = catId == null ? null : catId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.add_time
     *
     * @return the value of goods.add_time
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.add_time
     *
     * @param addTime the value for goods.add_time
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.last_update_time
     *
     * @return the value of goods.last_update_time
     * @mbggenerated
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.last_update_time
     *
     * @param lastUpdateTime the value for goods.last_update_time
     * @mbggenerated
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.is_real
     *
     * @return the value of goods.is_real
     * @mbggenerated
     */
    public Integer getIsReal() {
        return isReal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.is_real
     *
     * @param isReal the value for goods.is_real
     * @mbggenerated
     */
    public void setIsReal(Integer isReal) {
        this.isReal = isReal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.shop_price
     *
     * @return the value of goods.shop_price
     * @mbggenerated
     */
    public Float getShopPrice() {
        return shopPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.shop_price
     *
     * @param shopPrice the value for goods.shop_price
     * @mbggenerated
     */
    public void setShopPrice(Float shopPrice) {
        this.shopPrice = shopPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.shop_number
     *
     * @return the value of goods.shop_number
     * @mbggenerated
     */
    public Integer getShopNumber() {
        return shopNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.shop_number
     *
     * @param shopNumber the value for goods.shop_number
     * @mbggenerated
     */
    public void setShopNumber(Integer shopNumber) {
        this.shopNumber = shopNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.shop_format
     *
     * @return the value of goods.shop_format
     * @mbggenerated
     */
    public Byte getShopFormat() {
        return shopFormat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.shop_format
     *
     * @param shopFormat the value for goods.shop_format
     * @mbggenerated
     */
    public void setShopFormat(Byte shopFormat) {
        this.shopFormat = shopFormat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.seller_note
     *
     * @return the value of goods.seller_note
     * @mbggenerated
     */
    public String getSellerNote() {
        return sellerNote;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.seller_note
     *
     * @param sellerNote the value for goods.seller_note
     * @mbggenerated
     */
    public void setSellerNote(String sellerNote) {
        this.sellerNote = sellerNote == null ? null : sellerNote.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.add_user
     *
     * @return the value of goods.add_user
     * @mbggenerated
     */
    public String getAddUser() {
        return addUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.add_user
     *
     * @param addUser the value for goods.add_user
     * @mbggenerated
     */
    public void setAddUser(String addUser) {
        this.addUser = addUser == null ? null : addUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.user_id
     *
     * @return the value of goods.user_id
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.user_id
     *
     * @param userId the value for goods.user_id
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.check_status
     *
     * @return the value of goods.check_status
     * @mbggenerated
     */
    public Byte getCheckStatus() {
        return checkStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.check_status
     *
     * @param checkStatus the value for goods.check_status
     * @mbggenerated
     */
    public void setCheckStatus(Byte checkStatus) {
        this.checkStatus = checkStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.is_onsale
     *
     * @return the value of goods.is_onsale
     * @mbggenerated
     */
    public Byte getIsOnsale() {
        return isOnsale;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.is_onsale
     *
     * @param isOnsale the value for goods.is_onsale
     * @mbggenerated
     */
    public void setIsOnsale(Byte isOnsale) {
        this.isOnsale = isOnsale;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.is_update
     *
     * @return the value of goods.is_update
     * @mbggenerated
     */
    public Byte getIsUpdate() {
        return isUpdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.is_update
     *
     * @param isUpdate the value for goods.is_update
     * @mbggenerated
     */
    public void setIsUpdate(Byte isUpdate) {
        this.isUpdate = isUpdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods.version
     *
     * @return the value of goods.version
     * @mbggenerated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods.version
     *
     * @param version the value for goods.version
     * @mbggenerated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}