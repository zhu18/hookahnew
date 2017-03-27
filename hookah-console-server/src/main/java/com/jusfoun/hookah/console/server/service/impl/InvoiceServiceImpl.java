package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.InvoiceMapper;
import com.jusfoun.hookah.core.domain.Invoice;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.InvoiceService;

import javax.annotation.Resource;

/**
 * 购物车服务
 * @author:jsshao
 * @date: 2017-3-17
 */
public class InvoiceServiceImpl extends GenericServiceImpl<Invoice, String> implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;


    @Resource
    public void setDao(InvoiceMapper invoiceMapper) {
        super.setDao(invoiceMapper);
    }
}
