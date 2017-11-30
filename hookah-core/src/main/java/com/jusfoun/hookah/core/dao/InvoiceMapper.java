package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Invoice;
import com.jusfoun.hookah.core.domain.vo.InvoiceVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoInvoiceVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvoiceMapper extends GenericDao<Invoice> {
    List<OrderInfoInvoiceVo> getOrderInvoiceInfo(@Param("userId") String userId, @Param("invoiceStatus") Byte invoiceStatus);

    List<InvoiceVo> getInvoiceInfo(@Param("userName") String userName, @Param("userType") Byte userType,
                                   @Param("invoiceStatus") Byte invoiceStatus, @Param("invoiceType") Byte invoiceType );
}