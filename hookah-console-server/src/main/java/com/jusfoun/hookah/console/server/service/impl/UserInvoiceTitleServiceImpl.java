package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.UserInvoiceTitleMapper;
import com.jusfoun.hookah.core.domain.UserInvoiceTitle;
import com.jusfoun.hookah.core.domain.vo.UserInvoiceTitleVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.UserInvoiceTitleService;

import javax.annotation.Resource;
import java.util.List;

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

    public List<UserInvoiceTitleVo> getIncreInvoiceList(String userName, Byte userType, Byte invoiceStatus)throws HookahException{
        return userInvoiceTitleMapper.getIncreInvoiceList(userName, userType, invoiceStatus);
    }

    public UserInvoiceTitleVo findUserInvoiceTitleInfo(String titleId){
     return userInvoiceTitleMapper.findUserInvoiceTitleInfo(titleId);
    }
}
