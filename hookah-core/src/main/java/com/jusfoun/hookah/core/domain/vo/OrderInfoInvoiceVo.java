package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;

import javax.persistence.Transient;
import java.util.List;

/**
 * 订单发票vo
 * @author guoruibing
 * @created 2017年11月28日
 */
public class OrderInfoInvoiceVo extends OrderInfo {

	@Transient
	private static final long serialVersionUID = -6047037002252760669L;

	@Transient
	private String invoiceId;

	private String invoiceSn;

	private Byte invoiceStatus;

	private Byte invoiceType;

	private Byte invoiceChange;

	@Transient
	private List<MgOrderGoods> orderGoodsList;//订单里的商品列表

	public List<MgOrderGoods> getMgOrderGoodsList() {
		return orderGoodsList;
	}

	public void setMgOrderGoodsList(List<MgOrderGoods> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
	}


	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceSn() {
		return invoiceSn;
	}

	public void setInvoiceSn(String invoiceSn) {
		this.invoiceSn = invoiceSn;
	}

	public Byte getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Byte invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Byte getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Byte invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Byte getInvoiceChange() {
		return invoiceChange;
	}

	public void setInvoiceChange(Byte invoiceChange) {
		this.invoiceChange = invoiceChange;
	}
}
