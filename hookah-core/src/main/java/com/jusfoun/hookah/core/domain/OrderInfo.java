package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

public class OrderInfo extends GenericModel {
    public static final Integer ORDERSTATUS_UNCONFIRM =0;
    public static final Integer ORDERSTATUS_CONFIRM =1;
    public static final Integer ORDERSTATUS_CANCEL =2;
    public static final Integer ORDERSTATUS_INVALID =3;
    public static final Integer ORDERSTATUS_BACKGOODS =4;
    /**
     *
     */
    private static final long serialVersionUID = 5212608831898283343L;

    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;
    /**
     * 订单号,唯一
     */
    private String orderSn;

    private String userId;
    /**
     * 订单的状态;0未确认,1确认,2已取消,3无效,4退货
     */
    private Integer orderStatus;
    /**
     * 商品配送情况;0未发货,1已发货,2已收货,4退货
     */
    private Integer shippingStatus;
    /**
     * 支付状态;0未付款;1付款中;2已付款
     */
    private Integer payStatus;
    /**
     * 退款状态;0申请退款;1退款中;2已退款
     */
    private Integer refundStatus;
    /**
     * 退款审核意见;0不同意;1同意
     */
    private Integer refundView;
    /**
     * 退款审核意见原因
     */
    private String refundViewReason;
    /**
     * 收货人的姓名,用户页面填写,默认取值表user_address
     */
    private String consignee;
    /**
     * 收货人的国家,用户页面填写,默认取值于表user_address,其id对应的值在region
     */
    private Integer country;

    private Integer province;

    private Integer city;
    /**
     * 收货人的地区,用户页面填写,默认取值于表user_address,其id对应的值在region
     */
    private Integer district;

    private String address;

    private String zipcode;

    private String tel;

    private String mobile;

    private String email;
    /**
     * 收货人的最佳送货时间,用户页面填写,默认取值于表user_addr
     */
    private String bestTime;
    /**
     * 送货人的地址的标志性建筑
     */
    private String signBuilding;
    /**
     * 单附言,由用户提交订单前填写
     */
    private String postscript;
    /**
     * 用户选择的配送方式id,取值表shipping
     */
    private String shippingId;
    /**
     * 用户选择的配送方式的名称,取值表shipping
     */
    private String shippingName;
    /**
     * 用户选择的支付方式的id,取值表payment
     */
    private String payId;
    /**
     * 用户选择的支付方式名称,取值表payment
     */
    private String payName;
    /**
     * 缺货处理方式,等待所有商品备齐后再发,取消订单;与店主协商
     */
    private String howOos;
    /**
     * 根据字段猜测应该是余额处理方式,程序未作这部分实现
     */
    private String howSurplus;
    /**
     * 包装名称,取值表pack
     */
    private String packName;
    /**
     * 贺卡的名称,取值card
     */
    private String cardName;
    /**
     * 贺卡内容,由用户提交
     */
    private String cardMessage;
    /**
     * 发票抬头,用户页面填写
     */
    private String invPayee;
    /**
     * 发票内容,用户页面选择,取值shop_config的code字段的值 为invoice_content的value
     */
    private String invContent;
    /**
     * 商品的总金额
     */
    private Long goodsAmount;
    /**
     * 配送费用
     */
    private Long shippingFee;
    /**
     * 保价费用
     */
    private Long insureFee;
    /**
     * 支付费用,跟支付方式的配置相关,取值表payment
     */
    private Long payFee;
    /**
     * 包装费用,取值表pack
     */
    private Long packFee;
    /**
     * 贺卡费用,取值card
     */
    private Long cardFee;

    private Long goodsDiscountFee;
    /**
     * 已付款金额
     */
    private Long moneyPaid;
    /**
     * 该订单使用金额的数量,取用户设定余额,用户可用余额,订单金额中最小者
     */
    private Long surplus;
    /**
     * 使用的积分的数量,取用户使用积分,商品可用积分,用户拥有积分中最小者
     */
    private Integer integral;
    /**
     * 使用积分金额
     */
    private Long integralMoney;
    /**
     * 使用红包金额
     */
    private Long bonus;
    /**
     * 应付款金额
     */
    private Long orderAmount;
    /**
     * 订单由某广告带来的广告id,应该取值于ad
     */
    private Integer fromAd;
    /**
     * 订单的来源页面
     */
    private String referer;
    /**
     * 订单生成时间
     */
    private Date addTime;
    /**
     * 订单确认时间
     */
    private Date confirmTime;
    /**
     * 订单支付时间
     */
    private Date payTime;
    /**
     * 订单配送时间
     */
    private Date shippingTime;
    /**
     * 包装id,取值表pck
     */
    private String packId;
    /**
     * 贺卡id,用户在页面选择,取值
     */
    private String cardId;
    /**
     * 红包id, user_bonus的bonus_id
     */
    private String bonusId;
    /**
     * 发货时填写, 可在订单查询查看
     */
    private String invoiceNo;
    /**
     * 通过活动购买的商品的代号,group_buy是团购; auction是拍卖;snatch夺宝奇兵;正常普通产品该处理为空
     */
    private String extensionCode;
    /**
     * 通过活动购买的物品id,取值ecs_good_activity;如果是正常普通商品,该处为0
     */
    private String extensionId;
    /**
     * 商家给客户的留言,当该字段值时可以在订单查询看到
     */
    private String toBuyer;
    /**
     * 付款备注, 在订单管理编辑修改
     */
    private String payNote;

    private Integer agencyId;

    private Integer invType;

    private Long tax;

    private Integer isSeparate;

    private String parentId;

    private Long discount;

    private String callbackStatus;

    private Date lastmodify;
    /**
     * 发票介质 0纸质，1电子
     */
    private Integer invMedia;
    /**
     * 发票抬头
     */
    private String invHead;
    /**
     * 删除标志0已删除，1未删除,-1永久删除
     */
    private Integer delFlag;
    /**
     * 评论标志0为评论，1已评论
     */
    private Integer commentFlag;

    private String companyName;

    private String regAddress;

    private String regPhone;

    private String bank;

    private String account;

    private String idCode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.order_id
     *
     * @return the value of order_info.order_id
     *
     * @mbggenerated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.order_id
     *
     * @param orderId the value for order_info.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.order_sn
     *
     * @return the value of order_info.order_sn
     *
     * @mbggenerated
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.order_sn
     *
     * @param orderSn the value for order_info.order_sn
     *
     * @mbggenerated
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.user_id
     *
     * @return the value of order_info.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.user_id
     *
     * @param userId the value for order_info.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.order_status
     *
     * @return the value of order_info.order_status
     *
     * @mbggenerated
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.order_status
     *
     * @param orderStatus the value for order_info.order_status
     *
     * @mbggenerated
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.shipping_status
     *
     * @return the value of order_info.shipping_status
     *
     * @mbggenerated
     */
    public Integer getShippingStatus() {
        return shippingStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.shipping_status
     *
     * @param shippingStatus the value for order_info.shipping_status
     *
     * @mbggenerated
     */
    public void setShippingStatus(Integer shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.pay_status
     *
     * @return the value of order_info.pay_status
     *
     * @mbggenerated
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.pay_status
     *
     * @param payStatus the value for order_info.pay_status
     *
     * @mbggenerated
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.consignee
     *
     * @return the value of order_info.consignee
     *
     * @mbggenerated
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.consignee
     *
     * @param consignee the value for order_info.consignee
     *
     * @mbggenerated
     */
    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.country
     *
     * @return the value of order_info.country
     *
     * @mbggenerated
     */
    public Integer getCountry() {
        return country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.country
     *
     * @param country the value for order_info.country
     *
     * @mbggenerated
     */
    public void setCountry(Integer country) {
        this.country = country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.province
     *
     * @return the value of order_info.province
     *
     * @mbggenerated
     */
    public Integer getProvince() {
        return province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.province
     *
     * @param province the value for order_info.province
     *
     * @mbggenerated
     */
    public void setProvince(Integer province) {
        this.province = province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.city
     *
     * @return the value of order_info.city
     *
     * @mbggenerated
     */
    public Integer getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.city
     *
     * @param city the value for order_info.city
     *
     * @mbggenerated
     */
    public void setCity(Integer city) {
        this.city = city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.district
     *
     * @return the value of order_info.district
     *
     * @mbggenerated
     */
    public Integer getDistrict() {
        return district;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.district
     *
     * @param district the value for order_info.district
     *
     * @mbggenerated
     */
    public void setDistrict(Integer district) {
        this.district = district;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.address
     *
     * @return the value of order_info.address
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.address
     *
     * @param address the value for order_info.address
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.zipcode
     *
     * @return the value of order_info.zipcode
     *
     * @mbggenerated
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.zipcode
     *
     * @param zipcode the value for order_info.zipcode
     *
     * @mbggenerated
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.tel
     *
     * @return the value of order_info.tel
     *
     * @mbggenerated
     */
    public String getTel() {
        return tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.tel
     *
     * @param tel the value for order_info.tel
     *
     * @mbggenerated
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.mobile
     *
     * @return the value of order_info.mobile
     *
     * @mbggenerated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.mobile
     *
     * @param mobile the value for order_info.mobile
     *
     * @mbggenerated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.email
     *
     * @return the value of order_info.email
     *
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.email
     *
     * @param email the value for order_info.email
     *
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.best_time
     *
     * @return the value of order_info.best_time
     *
     * @mbggenerated
     */
    public String getBestTime() {
        return bestTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.best_time
     *
     * @param bestTime the value for order_info.best_time
     *
     * @mbggenerated
     */
    public void setBestTime(String bestTime) {
        this.bestTime = bestTime == null ? null : bestTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.sign_building
     *
     * @return the value of order_info.sign_building
     *
     * @mbggenerated
     */
    public String getSignBuilding() {
        return signBuilding;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.sign_building
     *
     * @param signBuilding the value for order_info.sign_building
     *
     * @mbggenerated
     */
    public void setSignBuilding(String signBuilding) {
        this.signBuilding = signBuilding == null ? null : signBuilding.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.postscript
     *
     * @return the value of order_info.postscript
     *
     * @mbggenerated
     */
    public String getPostscript() {
        return postscript;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.postscript
     *
     * @param postscript the value for order_info.postscript
     *
     * @mbggenerated
     */
    public void setPostscript(String postscript) {
        this.postscript = postscript == null ? null : postscript.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.shipping_id
     *
     * @return the value of order_info.shipping_id
     *
     * @mbggenerated
     */
    public String getShippingId() {
        return shippingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.shipping_id
     *
     * @param shippingId the value for order_info.shipping_id
     *
     * @mbggenerated
     */
    public void setShippingId(String shippingId) {
        this.shippingId = shippingId == null ? null : shippingId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.shipping_name
     *
     * @return the value of order_info.shipping_name
     *
     * @mbggenerated
     */
    public String getShippingName() {
        return shippingName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.shipping_name
     *
     * @param shippingName the value for order_info.shipping_name
     *
     * @mbggenerated
     */
    public void setShippingName(String shippingName) {
        this.shippingName = shippingName == null ? null : shippingName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.pay_id
     *
     * @return the value of order_info.pay_id
     *
     * @mbggenerated
     */
    public String getPayId() {
        return payId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.pay_id
     *
     * @param payId the value for order_info.pay_id
     *
     * @mbggenerated
     */
    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.pay_name
     *
     * @return the value of order_info.pay_name
     *
     * @mbggenerated
     */
    public String getPayName() {
        return payName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.pay_name
     *
     * @param payName the value for order_info.pay_name
     *
     * @mbggenerated
     */
    public void setPayName(String payName) {
        this.payName = payName == null ? null : payName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.how_oos
     *
     * @return the value of order_info.how_oos
     *
     * @mbggenerated
     */
    public String getHowOos() {
        return howOos;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.how_oos
     *
     * @param howOos the value for order_info.how_oos
     *
     * @mbggenerated
     */
    public void setHowOos(String howOos) {
        this.howOos = howOos == null ? null : howOos.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.how_surplus
     *
     * @return the value of order_info.how_surplus
     *
     * @mbggenerated
     */
    public String getHowSurplus() {
        return howSurplus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.how_surplus
     *
     * @param howSurplus the value for order_info.how_surplus
     *
     * @mbggenerated
     */
    public void setHowSurplus(String howSurplus) {
        this.howSurplus = howSurplus == null ? null : howSurplus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.pack_name
     *
     * @return the value of order_info.pack_name
     *
     * @mbggenerated
     */
    public String getPackName() {
        return packName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.pack_name
     *
     * @param packName the value for order_info.pack_name
     *
     * @mbggenerated
     */
    public void setPackName(String packName) {
        this.packName = packName == null ? null : packName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.card_name
     *
     * @return the value of order_info.card_name
     *
     * @mbggenerated
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.card_name
     *
     * @param cardName the value for order_info.card_name
     *
     * @mbggenerated
     */
    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.card_message
     *
     * @return the value of order_info.card_message
     *
     * @mbggenerated
     */
    public String getCardMessage() {
        return cardMessage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.card_message
     *
     * @param cardMessage the value for order_info.card_message
     *
     * @mbggenerated
     */
    public void setCardMessage(String cardMessage) {
        this.cardMessage = cardMessage == null ? null : cardMessage.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.inv_payee
     *
     * @return the value of order_info.inv_payee
     *
     * @mbggenerated
     */
    public String getInvPayee() {
        return invPayee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.inv_payee
     *
     * @param invPayee the value for order_info.inv_payee
     *
     * @mbggenerated
     */
    public void setInvPayee(String invPayee) {
        this.invPayee = invPayee == null ? null : invPayee.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.inv_content
     *
     * @return the value of order_info.inv_content
     *
     * @mbggenerated
     */
    public String getInvContent() {
        return invContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.inv_content
     *
     * @param invContent the value for order_info.inv_content
     *
     * @mbggenerated
     */
    public void setInvContent(String invContent) {
        this.invContent = invContent == null ? null : invContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.goods_amount
     *
     * @return the value of order_info.goods_amount
     *
     * @mbggenerated
     */
    public Long getGoodsAmount() {
        return goodsAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.goods_amount
     *
     * @param goodsAmount the value for order_info.goods_amount
     *
     * @mbggenerated
     */
    public void setGoodsAmount(Long goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.shipping_fee
     *
     * @return the value of order_info.shipping_fee
     *
     * @mbggenerated
     */
    public Long getShippingFee() {
        return shippingFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.shipping_fee
     *
     * @param shippingFee the value for order_info.shipping_fee
     *
     * @mbggenerated
     */
    public void setShippingFee(Long shippingFee) {
        this.shippingFee = shippingFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.insure_fee
     *
     * @return the value of order_info.insure_fee
     *
     * @mbggenerated
     */
    public Long getInsureFee() {
        return insureFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.insure_fee
     *
     * @param insureFee the value for order_info.insure_fee
     *
     * @mbggenerated
     */
    public void setInsureFee(Long insureFee) {
        this.insureFee = insureFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.pay_fee
     *
     * @return the value of order_info.pay_fee
     *
     * @mbggenerated
     */
    public Long getPayFee() {
        return payFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.pay_fee
     *
     * @param payFee the value for order_info.pay_fee
     *
     * @mbggenerated
     */
    public void setPayFee(Long payFee) {
        this.payFee = payFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.pack_fee
     *
     * @return the value of order_info.pack_fee
     *
     * @mbggenerated
     */
    public Long getPackFee() {
        return packFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.pack_fee
     *
     * @param packFee the value for order_info.pack_fee
     *
     * @mbggenerated
     */
    public void setPackFee(Long packFee) {
        this.packFee = packFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.card_fee
     *
     * @return the value of order_info.card_fee
     *
     * @mbggenerated
     */
    public Long getCardFee() {
        return cardFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.card_fee
     *
     * @param cardFee the value for order_info.card_fee
     *
     * @mbggenerated
     */
    public void setCardFee(Long cardFee) {
        this.cardFee = cardFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.goods_discount_fee
     *
     * @return the value of order_info.goods_discount_fee
     *
     * @mbggenerated
     */
    public Long getGoodsDiscountFee() {
        return goodsDiscountFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.goods_discount_fee
     *
     * @param goodsDiscountFee the value for order_info.goods_discount_fee
     *
     * @mbggenerated
     */
    public void setGoodsDiscountFee(Long goodsDiscountFee) {
        this.goodsDiscountFee = goodsDiscountFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.money_paid
     *
     * @return the value of order_info.money_paid
     *
     * @mbggenerated
     */
    public Long getMoneyPaid() {
        return moneyPaid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.money_paid
     *
     * @param moneyPaid the value for order_info.money_paid
     *
     * @mbggenerated
     */
    public void setMoneyPaid(Long moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.surplus
     *
     * @return the value of order_info.surplus
     *
     * @mbggenerated
     */
    public Long getSurplus() {
        return surplus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.surplus
     *
     * @param surplus the value for order_info.surplus
     *
     * @mbggenerated
     */
    public void setSurplus(Long surplus) {
        this.surplus = surplus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.integral
     *
     * @return the value of order_info.integral
     *
     * @mbggenerated
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.integral
     *
     * @param integral the value for order_info.integral
     *
     * @mbggenerated
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.integral_money
     *
     * @return the value of order_info.integral_money
     *
     * @mbggenerated
     */
    public Long getIntegralMoney() {
        return integralMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.integral_money
     *
     * @param integralMoney the value for order_info.integral_money
     *
     * @mbggenerated
     */
    public void setIntegralMoney(Long integralMoney) {
        this.integralMoney = integralMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.bonus
     *
     * @return the value of order_info.bonus
     *
     * @mbggenerated
     */
    public Long getBonus() {
        return bonus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.bonus
     *
     * @param bonus the value for order_info.bonus
     *
     * @mbggenerated
     */
    public void setBonus(Long bonus) {
        this.bonus = bonus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.order_amount
     *
     * @return the value of order_info.order_amount
     *
     * @mbggenerated
     */
    public Long getOrderAmount() {
        return orderAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.order_amount
     *
     * @param orderAmount the value for order_info.order_amount
     *
     * @mbggenerated
     */
    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.from_ad
     *
     * @return the value of order_info.from_ad
     *
     * @mbggenerated
     */
    public Integer getFromAd() {
        return fromAd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.from_ad
     *
     * @param fromAd the value for order_info.from_ad
     *
     * @mbggenerated
     */
    public void setFromAd(Integer fromAd) {
        this.fromAd = fromAd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.referer
     *
     * @return the value of order_info.referer
     *
     * @mbggenerated
     */
    public String getReferer() {
        return referer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.referer
     *
     * @param referer the value for order_info.referer
     *
     * @mbggenerated
     */
    public void setReferer(String referer) {
        this.referer = referer == null ? null : referer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.add_time
     *
     * @return the value of order_info.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.add_time
     *
     * @param addTime the value for order_info.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.confirm_time
     *
     * @return the value of order_info.confirm_time
     *
     * @mbggenerated
     */
    public Date getConfirmTime() {
        return confirmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.confirm_time
     *
     * @param confirmTime the value for order_info.confirm_time
     *
     * @mbggenerated
     */
    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.pay_time
     *
     * @return the value of order_info.pay_time
     *
     * @mbggenerated
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.pay_time
     *
     * @param payTime the value for order_info.pay_time
     *
     * @mbggenerated
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.shipping_time
     *
     * @return the value of order_info.shipping_time
     *
     * @mbggenerated
     */
    public Date getShippingTime() {
        return shippingTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.shipping_time
     *
     * @param shippingTime the value for order_info.shipping_time
     *
     * @mbggenerated
     */
    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.pack_id
     *
     * @return the value of order_info.pack_id
     *
     * @mbggenerated
     */
    public String getPackId() {
        return packId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.pack_id
     *
     * @param packId the value for order_info.pack_id
     *
     * @mbggenerated
     */
    public void setPackId(String packId) {
        this.packId = packId == null ? null : packId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.card_id
     *
     * @return the value of order_info.card_id
     *
     * @mbggenerated
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.card_id
     *
     * @param cardId the value for order_info.card_id
     *
     * @mbggenerated
     */
    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.bonus_id
     *
     * @return the value of order_info.bonus_id
     *
     * @mbggenerated
     */
    public String getBonusId() {
        return bonusId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.bonus_id
     *
     * @param bonusId the value for order_info.bonus_id
     *
     * @mbggenerated
     */
    public void setBonusId(String bonusId) {
        this.bonusId = bonusId == null ? null : bonusId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.invoice_no
     *
     * @return the value of order_info.invoice_no
     *
     * @mbggenerated
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.invoice_no
     *
     * @param invoiceNo the value for order_info.invoice_no
     *
     * @mbggenerated
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.extension_code
     *
     * @return the value of order_info.extension_code
     *
     * @mbggenerated
     */
    public String getExtensionCode() {
        return extensionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.extension_code
     *
     * @param extensionCode the value for order_info.extension_code
     *
     * @mbggenerated
     */
    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode == null ? null : extensionCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.extension_id
     *
     * @return the value of order_info.extension_id
     *
     * @mbggenerated
     */
    public String getExtensionId() {
        return extensionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.extension_id
     *
     * @param extensionId the value for order_info.extension_id
     *
     * @mbggenerated
     */
    public void setExtensionId(String extensionId) {
        this.extensionId = extensionId == null ? null : extensionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.to_buyer
     *
     * @return the value of order_info.to_buyer
     *
     * @mbggenerated
     */
    public String getToBuyer() {
        return toBuyer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.to_buyer
     *
     * @param toBuyer the value for order_info.to_buyer
     *
     * @mbggenerated
     */
    public void setToBuyer(String toBuyer) {
        this.toBuyer = toBuyer == null ? null : toBuyer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.pay_note
     *
     * @return the value of order_info.pay_note
     *
     * @mbggenerated
     */
    public String getPayNote() {
        return payNote;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.pay_note
     *
     * @param payNote the value for order_info.pay_note
     *
     * @mbggenerated
     */
    public void setPayNote(String payNote) {
        this.payNote = payNote == null ? null : payNote.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.agency_id
     *
     * @return the value of order_info.agency_id
     *
     * @mbggenerated
     */
    public Integer getAgencyId() {
        return agencyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.agency_id
     *
     * @param agencyId the value for order_info.agency_id
     *
     * @mbggenerated
     */
    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.inv_type
     *
     * @return the value of order_info.inv_type
     *
     * @mbggenerated
     */
    public Integer getInvType() {
        return invType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.inv_type
     *
     * @param invType the value for order_info.inv_type
     *
     * @mbggenerated
     */
    public void setInvType(Integer invType) {
        this.invType = invType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.tax
     *
     * @return the value of order_info.tax
     *
     * @mbggenerated
     */
    public Long getTax() {
        return tax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.tax
     *
     * @param tax the value for order_info.tax
     *
     * @mbggenerated
     */
    public void setTax(Long tax) {
        this.tax = tax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.is_separate
     *
     * @return the value of order_info.is_separate
     *
     * @mbggenerated
     */
    public Integer getIsSeparate() {
        return isSeparate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.is_separate
     *
     * @param isSeparate the value for order_info.is_separate
     *
     * @mbggenerated
     */
    public void setIsSeparate(Integer isSeparate) {
        this.isSeparate = isSeparate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.parent_id
     *
     * @return the value of order_info.parent_id
     *
     * @mbggenerated
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.parent_id
     *
     * @param parentId the value for order_info.parent_id
     *
     * @mbggenerated
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.discount
     *
     * @return the value of order_info.discount
     *
     * @mbggenerated
     */
    public Long getDiscount() {
        return discount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.discount
     *
     * @param discount the value for order_info.discount
     *
     * @mbggenerated
     */
    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.callback_status
     *
     * @return the value of order_info.callback_status
     *
     * @mbggenerated
     */
    public String getCallbackStatus() {
        return callbackStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.callback_status
     *
     * @param callbackStatus the value for order_info.callback_status
     *
     * @mbggenerated
     */
    public void setCallbackStatus(String callbackStatus) {
        this.callbackStatus = callbackStatus == null ? null : callbackStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.lastmodify
     *
     * @return the value of order_info.lastmodify
     *
     * @mbggenerated
     */
    public Date getLastmodify() {
        return lastmodify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.lastmodify
     *
     * @param lastmodify the value for order_info.lastmodify
     *
     * @mbggenerated
     */
    public void setLastmodify(Date lastmodify) {
        this.lastmodify = lastmodify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.inv_media
     *
     * @return the value of order_info.inv_media
     *
     * @mbggenerated
     */
    public Integer getInvMedia() {
        return invMedia;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.inv_media
     *
     * @param invMedia the value for order_info.inv_media
     *
     * @mbggenerated
     */
    public void setInvMedia(Integer invMedia) {
        this.invMedia = invMedia;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.inv_head
     *
     * @return the value of order_info.inv_head
     *
     * @mbggenerated
     */
    public String getInvHead() {
        return invHead;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.inv_head
     *
     * @param invHead the value for order_info.inv_head
     *
     * @mbggenerated
     */
    public void setInvHead(String invHead) {
        this.invHead = invHead == null ? null : invHead.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.del_flag
     *
     * @return the value of order_info.del_flag
     *
     * @mbggenerated
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.del_flag
     *
     * @param delFlag the value for order_info.del_flag
     *
     * @mbggenerated
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.comment_flag
     *
     * @return the value of order_info.comment_flag
     *
     * @mbggenerated
     */
    public Integer getCommentFlag() {
        return commentFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.comment_flag
     *
     * @param commentFlag the value for order_info.comment_flag
     *
     * @mbggenerated
     */
    public void setCommentFlag(Integer commentFlag) {
        this.commentFlag = commentFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.company_name
     *
     * @return the value of order_info.company_name
     *
     * @mbggenerated
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.company_name
     *
     * @param companyName the value for order_info.company_name
     *
     * @mbggenerated
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.reg_address
     *
     * @return the value of order_info.reg_address
     *
     * @mbggenerated
     */
    public String getRegAddress() {
        return regAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.reg_address
     *
     * @param regAddress the value for order_info.reg_address
     *
     * @mbggenerated
     */
    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress == null ? null : regAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.reg_phone
     *
     * @return the value of order_info.reg_phone
     *
     * @mbggenerated
     */
    public String getRegPhone() {
        return regPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.reg_phone
     *
     * @param regPhone the value for order_info.reg_phone
     *
     * @mbggenerated
     */
    public void setRegPhone(String regPhone) {
        this.regPhone = regPhone == null ? null : regPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.bank
     *
     * @return the value of order_info.bank
     *
     * @mbggenerated
     */
    public String getBank() {
        return bank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.bank
     *
     * @param bank the value for order_info.bank
     *
     * @mbggenerated
     */
    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.account
     *
     * @return the value of order_info.account
     *
     * @mbggenerated
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.account
     *
     * @param account the value for order_info.account
     *
     * @mbggenerated
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.id_code
     *
     * @return the value of order_info.id_code
     *
     * @mbggenerated
     */
    public String getIdCode() {
        return idCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.id_code
     *
     * @param idCode the value for order_info.id_code
     *
     * @mbggenerated
     */
    public void setIdCode(String idCode) {
        this.idCode = idCode == null ? null : idCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.refund_status
     *
     * @return the value of order_info.refund_status
     *
     * @mbggenerated
     */
    public Integer getRefundStatus() {
        return refundStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.refund_status
     *
     * @param refundStatus the value for order_info.refund_status
     *
     * @mbggenerated
     */
    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.refund_view
     *
     * @return the value of order_info.refund_view
     *
     * @mbggenerated
     */
    public Integer getRefundView() {
        return refundView;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.refund_view
     *
     * @param refundView the value for order_info.refund_view
     *
     * @mbggenerated
     */
    public void setRefundView(Integer refundView) {
        this.refundView = refundView;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.refund_view_reason
     *
     * @return the value of order_info.refund_view_reason
     *
     * @mbggenerated
     */
    public String getRefundViewReason() {
        return refundViewReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.refund_view_reason
     *
     * @param refundViewReason the value for order_info.refund_view_reason
     *
     * @mbggenerated
     */
    public void setRefundViewReason(String refundViewReason) {
        this.refundViewReason = refundViewReason == null ? null : refundViewReason.trim();
    }
}