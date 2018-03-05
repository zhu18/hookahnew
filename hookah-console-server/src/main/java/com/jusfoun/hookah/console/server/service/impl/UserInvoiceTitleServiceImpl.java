package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.UserInvoiceTitleMapper;
import com.jusfoun.hookah.core.domain.UserInvoiceTitle;
import com.jusfoun.hookah.core.domain.vo.UserInvoiceTitleVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.UserInvoiceTitleService;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        List<UserInvoiceTitleVo> userInvoiceTitleVoList = new ArrayList<>();
        if(-1 == userType || null == userType){
            userInvoiceTitleVoList.addAll(userInvoiceTitleMapper.getIncreInvoiceList2(userName, userType, invoiceStatus));
            userInvoiceTitleVoList.addAll(userInvoiceTitleMapper.getIncreInvoiceList4(userName, userType, invoiceStatus));
        }else if(HookahConstants.USER_TYPE_2 == userType){

            userInvoiceTitleVoList.addAll(userInvoiceTitleMapper.getIncreInvoiceList2(userName, userType, invoiceStatus));
        }else if(HookahConstants.USER_TYPE_4 == userType){

            userInvoiceTitleVoList.addAll(userInvoiceTitleMapper.getIncreInvoiceList4(userName, userType, invoiceStatus));
        }
        return userInvoiceTitleVoList;
    }

    public UserInvoiceTitleVo findUserInvoiceTitleInfo(String titleId, Byte userType){
        if(HookahConstants.USER_TYPE_2 == userType){

            return userInvoiceTitleMapper.findUserInvoiceTitleInfo2(titleId);
        }else{

            return userInvoiceTitleMapper.findUserInvoiceTitleInfo4(titleId);
        }
    }
}
