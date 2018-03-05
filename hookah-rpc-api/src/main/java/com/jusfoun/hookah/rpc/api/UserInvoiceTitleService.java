package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.UserInvoiceTitle;
import com.jusfoun.hookah.core.domain.vo.UserInvoiceTitleVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * Created by Gring on 2017/11/27.
 */
public interface UserInvoiceTitleService extends GenericService<UserInvoiceTitle,String> {
    /**
     * 后台增票资质查询列表
     * @param userName
     * @param userType
     * @param invoiceStatus
     * @return
     * @throws HookahException
     */
    List<UserInvoiceTitleVo> getIncreInvoiceList(String userName, Byte userType, Byte invoiceStatus)throws HookahException;

    /**
     * 获取增票资质详情
     * @param titleId
     * @return
     */
    UserInvoiceTitleVo findUserInvoiceTitleInfo(String titleId, Byte userType);
}
