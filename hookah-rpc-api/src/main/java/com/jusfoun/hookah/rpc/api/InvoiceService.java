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

    /**
     * 申请发票
     * @param invoiceDTOVo
     * @throws HookahException
     */
    public void addInvoice(InvoiceDTOVo invoiceDTOVo) throws HookahException;

    /**
     * 个人中心-我的发票
     * @param pageNum
     * @param pageSize
     * @param userId
     * @param invoiceStatus
     * @return
     * @throws HookahException
     */
    public Pagination<OrderInfoInvoiceVo> getDetailListInPage(Integer pageNum, Integer pageSize, String userId, Byte invoiceStatus)throws HookahException;

    /**
     * 个人中心-我的发票详情
     * @param invoiceId
     * @return
     * @throws HookahException
     */
    public InvoiceDetailVo findInvoiceInfo(String invoiceId) throws HookahException;

    /**
     * 后台发票查询列表
     * @param userName
     * @param userType
     * @param invoiceStatus
     * @param invoiceType
     * @return
     * @throws HookahException
     */
    public List<InvoiceVo> getInvoiceListInPage(String userName, Byte userType, Byte invoiceStatus, Byte invoiceType)throws HookahException;

    /**
     * 后台发票详情
     * @param invoiceId
     * @return
     * @throws HookahException
     */
    public InvoiceDetailVo findOrderInvoiceInfo(String invoiceId) throws HookahException;
}
