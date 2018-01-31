package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.UserInvoiceAddress;
import com.jusfoun.hookah.core.domain.vo.UserInvoiceAddressVo;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface UserInvoiceAddressMapper extends GenericDao<UserInvoiceAddress> {

    UserInvoiceAddressVo getUserInvoiceAddressById(String id);

    int updateDefaultAddr(String userId);
}