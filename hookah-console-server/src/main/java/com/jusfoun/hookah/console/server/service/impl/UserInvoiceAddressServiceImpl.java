package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.UserInvoiceAddressMapper;
import com.jusfoun.hookah.core.domain.UserInvoiceAddress;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.UserInvoiceAddressService;

import javax.annotation.Resource;

/**
 * Created by Gring on 2017/11/27.
 */
public class UserInvoiceAddressServiceImpl extends GenericServiceImpl<UserInvoiceAddress, String> implements UserInvoiceAddressService {

    @Resource
    private UserInvoiceAddressMapper userInvoiceAddressMapper;
    @Resource
    public void setDao(UserInvoiceAddressMapper userInvoiceAddressMapper) {
        super.setDao(userInvoiceAddressMapper);
    }
}
