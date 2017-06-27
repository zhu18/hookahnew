package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;

import javax.persistence.Transient;
import java.util.List;

/**
 * 订单vo
 * @author zhanghanqing
 * @created 2016年6月23日
 */
public class OrderInfoVo extends OrderInfo {

	/**
	 *
	 */
	@Transient
	private static final long serialVersionUID = -6047037002252760669L;

	@Transient
	private String orderStatusName;

	@Transient
	private String shippingStatusName;

	@Transient
	private String payStatusName;

	@Transient
	private String countryName;

	@Transient
	private String provinceName;

	@Transient
	private String cityName;

	@Transient
	private String districtName;

	@Transient
	private Integer invoiceId;

	@Transient
	private Integer addressId;

	@Transient
	private List<MgOrderGoods> orderGoodsList;//订单里的商品列表
	@Transient
	private String goumaiStartTime;//购买开始时间
	@Transient
	private String goumaiEndTime;//购买结束时间
	@Transient
	private String userPhone;//用户手机号
	@Transient
	private String userEmail;//用户邮箱
	@Transient
	private String userName;//用户名称
	@Transient
	private Integer userType;//用户类型
	@Transient
	private String realName;//用户认证名称

	public String getOrderStatusName() {
		return orderStatusName;
	}

	@Override
	public void setOrderStatus(Integer orderStatus) {
		super.setOrderStatus(orderStatus);
		/**
		 * 订单的状态;0未确认,1确认,2已取消,3无效,4退货
		 */
		if(orderStatus==0){
			orderStatusName = "未确认";
		}else if(orderStatus == 1){
			orderStatusName = "确认";
		}else if(orderStatus == 2){
			orderStatusName = "已取消";
		}else if(orderStatus == 3){
			orderStatusName = "无效";
		}else if(orderStatus == 4){
			orderStatusName = "退货";
		}

	}

	public String getShippingStatusName() {
		return shippingStatusName;
	}

	@Override
	public void setShippingStatus(Integer shippingStatus) {
		super.setShippingStatus(shippingStatus);
		//商品配送情况;0未发货,1已发货,2已收货,4退货
		if(shippingStatus == 0){
			shippingStatusName = "未发货";
		}else if(shippingStatus == 1){
			shippingStatusName = "已发货";
		}else if(shippingStatus == 2){
			shippingStatusName = "已收货";
		}else if(shippingStatus == 4){
			shippingStatusName = "退货";
		}
	}

	public List<MgOrderGoods> getMgOrderGoodsList() {
		return orderGoodsList;
	}

	public void setMgOrderGoodsList(List<MgOrderGoods> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
	}

	public String getPayStatusName() {
		return payStatusName;
	}
	@Override
	public void setPayStatus(Integer payStatus) {
		super.setPayStatus(payStatus);
		//支付状态;0未付款;1付款中;2已付款
		if(payStatus == 0){
			payStatusName = "未付款";
		}else if(payStatus == 1){
			payStatusName = "付款中";
		}else if(payStatus == 2){
			payStatusName = "已付款";
		}
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getGoumaiStartTime() {
		return goumaiStartTime;
	}

	public void setGoumaiStartTime(String goumaiStartTime) {
		this.goumaiStartTime = goumaiStartTime;
	}
	public String getGoumaiEndTime() {
		return goumaiEndTime;
	}
	public void setGoumaiEndTime(String goumaiEndTime) {
		this.goumaiEndTime = goumaiEndTime;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
