package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Invoice;
import com.jusfoun.hookah.core.domain.vo.InvoiceVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoInvoiceVo;
import com.jusfoun.hookah.core.domain.vo.UserInvoiceVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvoiceMapper extends GenericDao<Invoice> {
    List<OrderInfoInvoiceVo> getOrderInvoiceInfoList(@Param("userId") String userId, @Param("invoiceStatus") Byte invoiceStatus);

    List<InvoiceVo> getInvoiceInfo2(@Param("userName") String userName, @Param("userType") Byte userType,
                                   @Param("invoiceStatus") Byte invoiceStatus, @Param("invoiceType") Byte invoiceType );

 List<InvoiceVo> getInvoiceInfo4(@Param("userName") String userName, @Param("userType") Byte userType,
                                   @Param("invoiceStatus") Byte invoiceStatus, @Param("invoiceType") Byte invoiceType );

    List<OrderInfoInvoiceVo> getOrderInvoiceDetailInfo(@Param("invoiceId") String invoiceId);

    UserInvoiceVo getUserInvoiceInfoByInvoiceId2(@Param("invoiceId") String invoiceId);

    UserInvoiceVo getUserInvoiceInfoByInvoiceId4(@Param("invoiceId") String invoiceId);
}