package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.Invoice;
import com.jusfoun.hookah.core.domain.vo.InvoiceDTOVo;
import com.jusfoun.hookah.core.domain.vo.InvoiceDetailVo;
import com.jusfoun.hookah.core.domain.vo.InvoiceVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoInvoiceVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * Created by wangjl on 2017-3-15.
 */
public interface InvoiceService extends GenericService<Invoice,String> {

    public void addInvoice(InvoiceDTOVo invoiceDTOVo) throws HookahException;

    public Pagination<OrderInfoInvoiceVo> getDetailListInPage(Integer pageNum, Integer pageSize, String userId, Byte invoiceStatus)throws HookahException;

    public InvoiceDetailVo findInvoiceInfo(String invoiceId) throws HookahException;

    public List<InvoiceVo> getInvoiceListInPage(String userName, Byte userType, Byte invoiceStatus, Byte invoiceType)throws HookahException;
}
