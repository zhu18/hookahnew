package com.jusfoun.hookah.webiste.controller;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.UserInvoiceAddress;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.RegionService;
import com.jusfoun.hookah.rpc.api.UserInvoiceAddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Gring on 2017/11/27.
 */
@RestController
@RequestMapping(value = "/api/userInvoiceAddress")
public class UserInvoiceAddressController extends BaseController {

    @Resource
    UserInvoiceAddressService userInvoiceAddressService;

    @Resource
    RegionService regionService;
    /**
     * 根据userId获取收票人的完整信息
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnData getUserInvoiceAddress() {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("userId", userId));
            filters.add(Condition.eq("deleteStatus", HookahConstants.DELETE_STATUS_1));
            List<UserInvoiceAddress> userInvoiceAddressList = new ArrayList<>();
            userInvoiceAddressList = userInvoiceAddressService.selectList(filters);
            userInvoiceAddressList.stream().forEach(userInvoiceAddress -> {
                if(StringUtils.isNotBlank(userInvoiceAddress.getRegion())){
                    if(Objects.nonNull(regionService.selectById(userInvoiceAddress.getRegion()))){
                        if(StringUtils.isNotBlank(regionService.selectById(userInvoiceAddress.getRegion()).getMergerName())){
                            String[] strings = regionService.selectById(userInvoiceAddress.getRegion()).getMergerName().split(",");
                            StringBuilder stringBuilder = new StringBuilder();
                            for(int i=1;i<strings.length;i++){
                                stringBuilder.append(strings[i]).append(" ");
                                userInvoiceAddress.setReceiveAddress(stringBuilder.toString());
                            }
                        }
                    }
                }
            });
            returnData.setData(userInvoiceAddressList);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return  returnData;
    }

    @RequestMapping(value = "/save")
    public ReturnData save(String userInvoiceAddress) {
        UserInvoiceAddress obj = JSON.parseObject(userInvoiceAddress, UserInvoiceAddress.class);
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            obj.setUserId(this.getCurrentUser().getUserId());
            userInvoiceAddressService.insert(obj);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value = "/del")
    public ReturnData delete(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            UserInvoiceAddress userInvoiceAddress = new UserInvoiceAddress();
            userInvoiceAddress.setId(id);
            userInvoiceAddress.setDeleteStatus(HookahConstants.DELETE_STATUS_0);
            userInvoiceAddressService.updateByIdSelective(userInvoiceAddress);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public ReturnData editUserInvoiceAddress(String userInvoiceAddress) {

        UserInvoiceAddress obj = JSON.parseObject(userInvoiceAddress, UserInvoiceAddress.class);

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            userInvoiceAddressService.updateByIdSelective(obj);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 根据id查询收票人信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    @ResponseBody
    public ReturnData findUserInvoiceAddressInfoById(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(userInvoiceAddressService.findUserInvoiceAddressInfoById(id));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 根据收票人ID设置默认地址
     *
     * @param id
     * @return
     */
    @RequestMapping("/updateDefalutAddr")
    @ResponseBody
    public ReturnData updateDefalutAddrById(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            userInvoiceAddressService.updateDefaultAddress(id, getCurrentUser().getUserId());
            returnData.setData(userInvoiceAddressService.selectById(id));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

}
