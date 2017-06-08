package com.jusfoun.hookah.core.domain.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class MgOrderGoods extends GoodsVo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_goods.rec_id
     *
     * 
     */
    private String recId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_goods.order_id
     *
     * 
     */
    private String orderId;

    private String orderSn;

//    private Long goodsNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_goods.market_price
     *
     *
     */
    private Long marketPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_goods.goods_price
     *
     *
     */
    private Long goodsPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_goods.discount_fee
     *
     *
     */
    private Long discountFee;


    private Short isGift;

    private Integer formatId;

    private Integer goodsFormat;

    private Long formatNumber;




//    private List<MgCategoryAttrType.AttrTypeBean> attrTypeList;
//    private List<MgGoods.FormatBean> formatList;
//    private List<MgGoods.ImgBean> imgList;
//    private MgGoods.ApiInfoBean apiInfo;
//
//    private MgGoods.DataModelBean dataModel;                //数据模型
//    private MgGoods.ASSaaSBean asSaaS;                      // 应用场景SaaS
//    private MgGoods.ASAloneSoftwareBean asAloneSoftware;    // 应用场景 独立软件
//    private MgGoods.ATSaaSBean atSaaS;                      // 分析工具 SaaS
//    private MgGoods.ATAloneSoftwareBean atAloneSoftware;    // 分析工具 独立软件
//
//    private boolean orNotFavorite;
//    private String userId;
//    private String catName;
//    private String checkReason;
//    private Integer rowStart;
//    private Integer rowEnd;
//    private String areaCountry;
//    private String areaProvince;
//    private String areaCity;
//    private String catFullName;
//    private Long clickRate;
//    private Double goodsGrades;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

//    public List<MgCategoryAttrType.AttrTypeBean> getAttrTypeList() {
//        return attrTypeList;
//    }
//
//    public void setAttrTypeList(List<MgCategoryAttrType.AttrTypeBean> attrTypeList) {
//        this.attrTypeList = attrTypeList;
//    }

//    public List<MgGoods.FormatBean> getFormatList() {
//        return formatList;
//    }
//
//    public void setFormatList(List<MgGoods.FormatBean> formatList) {
//        this.formatList = formatList;
//    }
//
//    public List<MgGoods.ImgBean> getImgList() {
//        return imgList;
//    }
//
//    public void setImgList(List<MgGoods.ImgBean> imgList) {
//        this.imgList = imgList;
//    }
//
//    public MgGoods.ApiInfoBean getApiInfo() {
//        return apiInfo;
//    }
//
//    public void setApiInfo(MgGoods.ApiInfoBean apiInfo) {
//        this.apiInfo = apiInfo;
//    }

//    public MgGoods.DataModelBean getDataModel() {
//        return dataModel;
//    }
//
//    public void setDataModel(MgGoods.DataModelBean dataModel) {
//        this.dataModel = dataModel;
//    }
//
//    public MgGoods.ASSaaSBean getAsSaaS() {
//        return asSaaS;
//    }
//
//    public void setAsSaaS(MgGoods.ASSaaSBean asSaaS) {
//        this.asSaaS = asSaaS;
//    }
//
//    public MgGoods.ASAloneSoftwareBean getAsAloneSoftware() {
//        return asAloneSoftware;
//    }
//
//    public void setAsAloneSoftware(MgGoods.ASAloneSoftwareBean asAloneSoftware) {
//        this.asAloneSoftware = asAloneSoftware;
//    }
//
//    public MgGoods.ATSaaSBean getAtSaaS() {
//        return atSaaS;
//    }
//
//    public void setAtSaaS(MgGoods.ATSaaSBean atSaaS) {
//        this.atSaaS = atSaaS;
//    }
//
//    public MgGoods.ATAloneSoftwareBean getAtAloneSoftware() {
//        return atAloneSoftware;
//    }
//
//    public void setAtAloneSoftware(MgGoods.ATAloneSoftwareBean atAloneSoftware) {
//        this.atAloneSoftware = atAloneSoftware;
//    }

//    public boolean isOrNotFavorite() {
//        return orNotFavorite;
//    }
//
//    public void setOrNotFavorite(boolean orNotFavorite) {
//        this.orNotFavorite = orNotFavorite;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getCatName() {
//        return catName;
//    }
//
//    public void setCatName(String catName) {
//        this.catName = catName;
//    }
//
//    public String getCheckReason() {
//        return checkReason;
//    }
//
//    public void setCheckReason(String checkReason) {
//        this.checkReason = checkReason;
//    }
//
//    public Integer getRowStart() {
//        return rowStart;
//    }
//
//    public void setRowStart(Integer rowStart) {
//        this.rowStart = rowStart;
//    }
//
//    public Integer getRowEnd() {
//        return rowEnd;
//    }
//
//    public void setRowEnd(Integer rowEnd) {
//        this.rowEnd = rowEnd;
//    }
//
//    public String getAreaCountry() {
//        return areaCountry;
//    }
//
//    public void setAreaCountry(String areaCountry) {
//        this.areaCountry = areaCountry;
//    }
//
//    public String getAreaProvince() {
//        return areaProvince;
//    }
//
//    public void setAreaProvince(String areaProvince) {
//        this.areaProvince = areaProvince;
//    }
//
//    public String getAreaCity() {
//        return areaCity;
//    }
//
//    public void setAreaCity(String areaCity) {
//        this.areaCity = areaCity;
//    }
//
//    public String getCatFullName() {
//        return catFullName;
//    }
//
//    public void setCatFullName(String catFullName) {
//        this.catFullName = catFullName;
//    }
//
//    public Long getClickRate() {
//        return clickRate;
//    }
//
//    public void setClickRate(Long clickRate) {
//        this.clickRate = clickRate;
//    }
//
//    public Double getGoodsGrades() {
//        return goodsGrades;
//    }
//
//    public void setGoodsGrades(Double goodsGrades) {
//        this.goodsGrades = goodsGrades;
//    }

//    public Byte getGoodsType() {
//        return goodsType;
//    }
//
//    public void setGoodsType(Byte goodsType) {
//        this.goodsType = goodsType;
//    }
//
//    public String getUploadUrl() {
//        return uploadUrl;
//    }
//
//    public void setUploadUrl(String uploadUrl) {
//        this.uploadUrl = uploadUrl;
//    }

    /**
     * 
     * This method returns the value of the database column order_goods.rec_id
     *
     * @return the value of order_goods.rec_id
     *
     * 
     */
    public String getRecId() {
        return recId;
    }

    /**
     * 
     * This method sets the value of the database column order_goods.rec_id
     *
     * @param recId the value for order_goods.rec_id
     *
     * 
     */
    public void setRecId(String recId) {
        this.recId = recId == null ? null : recId.trim();
    }

    /**
     * 
     * This method returns the value of the database column order_goods.order_id
     *
     * @return the value of order_goods.order_id
     *
     * 
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * This method sets the value of the database column order_goods.order_id
     *
     * @param orderId the value for order_goods.order_id
     *
     * 
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     *
     * This method returns the value of the database column order_goods.goods_id
     *
     * @return the value of order_goods.goods_id
     *
     *
     */
//    public String getGoodsId() {
//        return goodsId;
//    }

    /**
     *
     * This method sets the value of the database column order_goods.goods_id
     *
     * @param goodsId the value for order_goods.goods_id
     *
     *
     */
//    public void setGoodsId(String goodsId) {
//        this.goodsId = goodsId == null ? null : goodsId.trim();
//    }

    /**
     *
     * This method returns the value of the database column order_goods.goods_name
     *
     * @return the value of order_goods.goods_name
     *
     *
     */
//    public String getGoodsName() {
//        return goodsName;
//    }

    /**
     *
     * This method sets the value of the database column order_goods.goods_name
     *
     * @param goodsName the value for order_goods.goods_name
     *
     *
     */
//    public void setGoodsName(String goodsName) {
//        this.goodsName = goodsName == null ? null : goodsName.trim();
//    }

    /**
     *
     * This method returns the value of the database column order_goods.goods_sn
     *
     * @return the value of order_goods.goods_sn
     *
     *
     */
//    public String getGoodsSn() {
//        return goodsSn;
//    }

    /**
     *
     * This method sets the value of the database column order_goods.goods_sn
     *
     * @param goodsSn the value for order_goods.goods_sn
     *
     *
     */
//    public void setGoodsSn(String goodsSn) {
//        this.goodsSn = goodsSn == null ? null : goodsSn.trim();
//    }

    /**
     *
     * This method returns the value of the database column order_goods.product_id
     *
     * @return the value of order_goods.product_id
     *
     *
     */
//    public String getProductId() {
//        return productId;
//    }
//
//    /**
//     *
//     * This method sets the value of the database column order_goods.product_id
//     *
//     * @param productId the value for order_goods.product_id
//     *
//     *
//     */
//    public void setProductId(String productId) {
//        this.productId = productId == null ? null : productId.trim();
//    }


    /**
     *
     * This method returns the value of the database column order_goods.market_price
     *
     * @return the value of order_goods.market_price
     *
     *
     */
    public Long getMarketPrice() {
        return marketPrice;
    }

    /**
     *
     * This method sets the value of the database column order_goods.market_price
     *
     * @param marketPrice the value for order_goods.market_price
     *
     *
     */
    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     *
     * This method returns the value of the database column order_goods.goods_price
     *
     * @return the value of order_goods.goods_price
     *
     *
     */
    public Long getGoodsPrice() {
        return goodsPrice;
    }

    /**
     *
     * This method sets the value of the database column order_goods.goods_price
     *
     * @param goodsPrice the value for order_goods.goods_price
     *
     *
     */
    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     *
     * This method returns the value of the database column order_goods.discount_fee
     *
     * @return the value of order_goods.discount_fee
     *
     *
     */
    public Long getDiscountFee() {
        return discountFee;
    }

    /**
     *
     * This method sets the value of the database column order_goods.discount_fee
     *
     * @param discountFee the value for order_goods.discount_fee
     *
     *
     */
    public void setDiscountFee(Long discountFee) {
        this.discountFee = discountFee;
    }

    /**
     *
     * This method returns the value of the database column order_goods.send_number
     *
     * @return the value of order_goods.send_number
     *
     *
     */
//    public Integer getSendNumber() {
//        return sendNumber;
//    }
//
//    /**
//     *
//     * This method sets the value of the database column order_goods.send_number
//     *
//     * @param sendNumber the value for order_goods.send_number
//     *
//     *
//     */
//    public void setSendNumber(Integer sendNumber) {
//        this.sendNumber = sendNumber;
//    }
//
//    /**
//     *
//     * This method returns the value of the database column order_goods.is_real
//     *
//     * @return the value of order_goods.is_real
//     *
//     *
//     */
//    public Integer getIsReal() {
//        return isReal;
//    }

    /**
     *
     * This method sets the value of the database column order_goods.is_real
     *
     * @param isReal the value for order_goods.is_real
     *
     *
     */
//    public void setIsReal(Integer isReal) {
//        this.isReal = isReal;
//    }
//
//    /**
//     *
//     * This method returns the value of the database column order_goods.extension_code
//     *
//     * @return the value of order_goods.extension_code
//     *
//     *
//     */
//    public String getExtensionCode() {
//        return extensionCode;
//    }
//
//    /**
//     *
//     * This method sets the value of the database column order_goods.extension_code
//     *
//     * @param extensionCode the value for order_goods.extension_code
//     *
//     *
//     */
//    public void setExtensionCode(String extensionCode) {
//        this.extensionCode = extensionCode == null ? null : extensionCode.trim();
//    }
//
//    /**
//     *
//     * This method returns the value of the database column order_goods.parent_id
//     *
//     * @return the value of order_goods.parent_id
//     *
//     *
//     */
//    public String getParentId() {
//        return parentId;
//    }
//
//    /**
//     *
//     * This method sets the value of the database column order_goods.parent_id
//     *
//     * @param parentId the value for order_goods.parent_id
//     *
//     *
//     */
//    public void setParentId(String parentId) {
//        this.parentId = parentId == null ? null : parentId.trim();
//    }

    /**
     *
     * This method returns the value of the database column order_goods.is_gift
     *
     * @return the value of order_goods.is_gift
     *
     *
     */
    public Short getIsGift() {
        return isGift;
    }

//    public String getSourceId() {
//        return sourceId;
//    }
//
//    public void setSourceId(String sourceId) {
//        this.sourceId = sourceId;
//    }

    /**
     *
     * This method sets the value of the database column order_goods.is_gift
     *
     * @param isGift the value for order_goods.is_gift
     *
     *
     */
    public void setIsGift(Short isGift) {
        this.isGift = isGift;
    }

    public Integer getGoodsFormat() {
        return goodsFormat;
    }

    public void setGoodsFormat(Integer goodsFormat) {
        this.goodsFormat = goodsFormat;
    }

//    public String getGoodsImg() {
//        return goodsImg;
//    }
//
//    public void setGoodsImg(String goodsImg) {
//        this.goodsImg = goodsImg;
//    }

    public Long getFormatNumber() {
        return formatNumber;
    }

    public void setFormatNumber(Long formatNumber) {
        this.formatNumber = formatNumber;
    }

    public Integer getFormatId() {
        return formatId;
    }

    public void setFormatId(Integer formatId) {
        this.formatId = formatId;
    }

//    public Byte getIsOnsale() {
//        return isOnsale;
//    }
//
//    public void setIsOnsale(Byte isOnsale) {
//        this.isOnsale = isOnsale;
//    }
}