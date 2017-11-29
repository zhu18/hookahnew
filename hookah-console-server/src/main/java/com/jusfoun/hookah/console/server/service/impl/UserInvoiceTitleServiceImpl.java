package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.UserInvoiceTitleMapper;
import com.jusfoun.hookah.core.domain.UserInvoiceTitle;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.UserInvoiceTitleService;

import javax.annotation.Resource;

/**
 * Created by Gring on 2017/11/27.
 */
public class UserInvoiceTitleServiceImpl extends GenericServiceImpl<UserInvoiceTitle, String> implements UserInvoiceTitleService {

    @Resource
    private UserInvoiceTitleMapper userInvoiceTitleMapper;
    @Resource
    public void setDao(UserInvoiceTitleMapper userInvoiceTitleMapper) {
        super.setDao(userInvoiceTitleMapper);
    }
}
