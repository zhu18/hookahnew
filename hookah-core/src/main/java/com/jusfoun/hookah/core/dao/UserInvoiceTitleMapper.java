package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.UserInvoiceTitle;
import com.jusfoun.hookah.core.domain.vo.UserInvoiceTitleVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInvoiceTitleMapper extends GenericDao<UserInvoiceTitle> {

    List<UserInvoiceTitleVo> getIncreInvoiceList2(@Param("userName") String userName, @Param("userType") Byte userType,
                                                 @Param("invoiceStatus") Byte invoiceStatus);

    List<UserInvoiceTitleVo> getIncreInvoiceList4(@Param("userName") String userName, @Param("userType") Byte userType,
                                                 @Param("invoiceStatus") Byte invoiceStatus);

    UserInvoiceTitleVo findUserInvoiceTitleInfo2(String titleId);

    UserInvoiceTitleVo findUserInvoiceTitleInfo4(String titleId);
}