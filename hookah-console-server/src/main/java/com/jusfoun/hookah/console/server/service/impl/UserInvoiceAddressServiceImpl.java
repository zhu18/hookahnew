package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.UserInvoiceAddressMapper;
import com.jusfoun.hookah.core.domain.UserInvoiceAddress;
import com.jusfoun.hookah.core.domain.vo.UserInvoiceAddressVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.UserInvoiceAddressService;
import org.springframework.beans.BeanUtils;

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

    public UserInvoiceAddressVo findUserInvoiceAddressInfoById(String id) throws HookahException{

        UserInvoiceAddressVo userInvoiceAddressVo = null;
        userInvoiceAddressVo = userInvoiceAddressMapper.getUserInvoiceAddressById(id);
        if (userInvoiceAddressVo == null || userInvoiceAddressVo.getId() == null) {
            throw new HookahException("未查询到信息！");
        }
        if(userInvoiceAddressVo != null) {
            if (userInvoiceAddressVo.getGoodsAreas() != null) {
                String[] region = userInvoiceAddressVo.getGoodsAreas().split(" ");
                if (region.length >= 2)
                    userInvoiceAddressVo.setAreaCountry(region[1]);
                if (region.length >= 3)
                    userInvoiceAddressVo.setAreaProvince(region[2]);
                if (region.length >= 4)
                    userInvoiceAddressVo.setAreaCity(region[3]);
            }
        }
        return userInvoiceAddressVo;
    }

    @Override
    public int updateDefaultAddress(String addressId, String userId){

        userInvoiceAddressMapper.updateDefaultAddr(userId);

        UserInvoiceAddress userInvoiceAddress = new UserInvoiceAddress();
        userInvoiceAddress.setId(addressId);
        userInvoiceAddress.setDefaultStatus(HookahConstants.USER_INVOICE_DEFAULT);
        return super.updateByIdSelective(userInvoiceAddress);
    }
}
