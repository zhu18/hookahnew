package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.OrderInvoiceMapper;
import com.jusfoun.hookah.core.domain.OrderInvoice;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.OrderInvoiceService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 订单发票中间表服务
 * @author:jsshao
 * @date: 2017-3-17
 */
public class OrderInvoiceServiceImpl extends GenericServiceImpl<OrderInvoice, String> implements OrderInvoiceService {

    @Resource
    private OrderInvoiceMapper orderInvoiceMapper;

    @Resource
    public void setDao(OrderInvoiceMapper orderInvoiceMapper) {
        super.setDao(orderInvoiceMapper);
    }

    @Override
    public void insertOrderInvoice(String orderId, String invoiceId) {
        OrderInvoice orderInvoice = new OrderInvoice();
        orderInvoice.setAddTime(new Date());
        orderInvoice.setInvoiceId(invoiceId);
        orderInvoice.setOrderId(orderId);
        this.insert(orderInvoice);
    }
}
