package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.UserInvoiceAddress;
import com.jusfoun.hookah.core.domain.vo.UserInvoiceAddressVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * Created by Gring on 2017/11/27.
 */
public interface UserInvoiceAddressService extends GenericService<UserInvoiceAddress,String> {

    UserInvoiceAddressVo findUserInvoiceAddressInfoById(String id) throws HookahException;
}
