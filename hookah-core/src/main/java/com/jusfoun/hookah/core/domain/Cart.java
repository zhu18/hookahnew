package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;
import java.math.BigDecimal;
import java.util.Date;

public class Cart extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.rec_id
     *
     * @mbggenerated
     */
    private String recId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.session_id
     *
     * @mbggenerated
     */
    private String sessionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.goods_id
     *
     * @mbggenerated
     */
    private String goodsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.goods_sn
     *
     * @mbggenerated
     */
    private String goodsSn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.product_id
     *
     * @mbggenerated
     */
    private String productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.goods_name
     *
     * @mbggenerated
     */
    private String goodsName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.market_price
     *
     * @mbggenerated
     */
    private BigDecimal marketPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.goods_price
     *
     * @mbggenerated
     */
    private BigDecimal goodsPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.goods_number
     *
     * @mbggenerated
     */
    private Short goodsNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.is_real
     *
     * @mbggenerated
     */
    private Boolean isReal;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.extension_code
     *
     * @mbggenerated
     */
    private String extensionCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.parent_id
     *
     * @mbggenerated
     */
    private String parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.rec_type
     *
     * @mbggenerated
     */
    private Boolean recType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.is_gift
     *
     * @mbggenerated
     */
    private Short isGift;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.is_shipping
     *
     * @mbggenerated
     */
    private Boolean isShipping;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.can_handsel
     *
     * @mbggenerated
     */
    private Byte canHandsel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.goods_attr_id
     *
     * @mbggenerated
     */
    private String goodsAttrId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.goods_attr
     *
     * @mbggenerated
     */
    private String goodsAttr;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.rec_id
     *
     * @return the value of cart.rec_id
     *
     * @mbggenerated
     */
    public String getRecId() {
        return recId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.rec_id
     *
     * @param recId the value for cart.rec_id
     *
     * @mbggenerated
     */
    public void setRecId(String recId) {
        this.recId = recId == null ? null : recId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.user_id
     *
     * @return the value of cart.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.user_id
     *
     * @param userId the value for cart.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.session_id
     *
     * @return the value of cart.session_id
     *
     * @mbggenerated
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.session_id
     *
     * @param sessionId the value for cart.session_id
     *
     * @mbggenerated
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.goods_id
     *
     * @return the value of cart.goods_id
     *
     * @mbggenerated
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.goods_id
     *
     * @param goodsId the value for cart.goods_id
     *
     * @mbggenerated
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.goods_sn
     *
     * @return the value of cart.goods_sn
     *
     * @mbggenerated
     */
    public String getGoodsSn() {
        return goodsSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.goods_sn
     *
     * @param goodsSn the value for cart.goods_sn
     *
     * @mbggenerated
     */
    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn == null ? null : goodsSn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.product_id
     *
     * @return the value of cart.product_id
     *
     * @mbggenerated
     */
    public String getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.product_id
     *
     * @param productId the value for cart.product_id
     *
     * @mbggenerated
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.goods_name
     *
     * @return the value of cart.goods_name
     *
     * @mbggenerated
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.goods_name
     *
     * @param goodsName the value for cart.goods_name
     *
     * @mbggenerated
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.market_price
     *
     * @return the value of cart.market_price
     *
     * @mbggenerated
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.market_price
     *
     * @param marketPrice the value for cart.market_price
     *
     * @mbggenerated
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.goods_price
     *
     * @return the value of cart.goods_price
     *
     * @mbggenerated
     */
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.goods_price
     *
     * @param goodsPrice the value for cart.goods_price
     *
     * @mbggenerated
     */
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.goods_number
     *
     * @return the value of cart.goods_number
     *
     * @mbggenerated
     */
    public Short getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.goods_number
     *
     * @param goodsNumber the value for cart.goods_number
     *
     * @mbggenerated
     */
    public void setGoodsNumber(Short goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.is_real
     *
     * @return the value of cart.is_real
     *
     * @mbggenerated
     */
    public Boolean getIsReal() {
        return isReal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.is_real
     *
     * @param isReal the value for cart.is_real
     *
     * @mbggenerated
     */
    public void setIsReal(Boolean isReal) {
        this.isReal = isReal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.extension_code
     *
     * @return the value of cart.extension_code
     *
     * @mbggenerated
     */
    public String getExtensionCode() {
        return extensionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.extension_code
     *
     * @param extensionCode the value for cart.extension_code
     *
     * @mbggenerated
     */
    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode == null ? null : extensionCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.parent_id
     *
     * @return the value of cart.parent_id
     *
     * @mbggenerated
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.parent_id
     *
     * @param parentId the value for cart.parent_id
     *
     * @mbggenerated
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.rec_type
     *
     * @return the value of cart.rec_type
     *
     * @mbggenerated
     */
    public Boolean getRecType() {
        return recType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.rec_type
     *
     * @param recType the value for cart.rec_type
     *
     * @mbggenerated
     */
    public void setRecType(Boolean recType) {
        this.recType = recType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.is_gift
     *
     * @return the value of cart.is_gift
     *
     * @mbggenerated
     */
    public Short getIsGift() {
        return isGift;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.is_gift
     *
     * @param isGift the value for cart.is_gift
     *
     * @mbggenerated
     */
    public void setIsGift(Short isGift) {
        this.isGift = isGift;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.is_shipping
     *
     * @return the value of cart.is_shipping
     *
     * @mbggenerated
     */
    public Boolean getIsShipping() {
        return isShipping;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.is_shipping
     *
     * @param isShipping the value for cart.is_shipping
     *
     * @mbggenerated
     */
    public void setIsShipping(Boolean isShipping) {
        this.isShipping = isShipping;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.can_handsel
     *
     * @return the value of cart.can_handsel
     *
     * @mbggenerated
     */
    public Byte getCanHandsel() {
        return canHandsel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.can_handsel
     *
     * @param canHandsel the value for cart.can_handsel
     *
     * @mbggenerated
     */
    public void setCanHandsel(Byte canHandsel) {
        this.canHandsel = canHandsel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.goods_attr_id
     *
     * @return the value of cart.goods_attr_id
     *
     * @mbggenerated
     */
    public String getGoodsAttrId() {
        return goodsAttrId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.goods_attr_id
     *
     * @param goodsAttrId the value for cart.goods_attr_id
     *
     * @mbggenerated
     */
    public void setGoodsAttrId(String goodsAttrId) {
        this.goodsAttrId = goodsAttrId == null ? null : goodsAttrId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.add_time
     *
     * @return the value of cart.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.add_time
     *
     * @param addTime the value for cart.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.goods_attr
     *
     * @return the value of cart.goods_attr
     *
     * @mbggenerated
     */
    public String getGoodsAttr() {
        return goodsAttr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.goods_attr
     *
     * @param goodsAttr the value for cart.goods_attr
     *
     * @mbggenerated
     */
    public void setGoodsAttr(String goodsAttr) {
        this.goodsAttr = goodsAttr == null ? null : goodsAttr.trim();
    }
}