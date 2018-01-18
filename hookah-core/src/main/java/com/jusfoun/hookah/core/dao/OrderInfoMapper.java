package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderInfoMapper extends GenericDao<OrderInfo> {
    int getUserCount();

    Long sumOrderAmountByOrderIds(String[] orderIds);

    List<OrderInfo> getOrderInfoByInvoiceId(String invoiceId);
}