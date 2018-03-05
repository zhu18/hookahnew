package com.jusfoun.hookah.webiste.controller;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.UserInvoiceTitle;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.InvoiceService;
import com.jusfoun.hookah.rpc.api.UserInvoiceTitleService;
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
@RequestMapping(value = "/api/userInvoiceTitle")
public class UserInvoiceTitleController extends BaseController {

    @Resource
    UserInvoiceTitleService userInvoiceTitleService;

    @Resource
    InvoiceService invoiceService;
    /**
     * 根据userId获取单位抬头的完整信息
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnData getUserInvoiceTitle(Byte userInvoiceType) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("userId", userId));
            filters.add(Condition.eq("userInvoiceType", userInvoiceType));
            filters.add(Condition.eq("deleteStatus", HookahConstants.DELETE_STATUS_1));
            if(HookahConstants.INVOICE_TYPE_1 == userInvoiceType){
               /* UserInvoiceTitle userInvoiceTitle = userInvoiceTitleService.selectOne(filters);
                UserInvoiceTitleVo userInvoiceTitleVo = new UserInvoiceTitleVo();

                if(Objects.nonNull(userInvoiceTitle))
                BeanUtils.copyProperties(userInvoiceTitle, userInvoiceTitleVo);
                List<Condition> filter = new ArrayList();
                filter.add(Condition.eq("addUser", userId));
                filter.add(Condition.eq("invoiceType", userInvoiceType));
                Invoice invoice = invoiceService.selectOne(filter);
                if (Objects.nonNull(invoice)){

                    userInvoiceTitleVo.setInvoiceStatus(invoice.getInvoiceStatus());
                    userInvoiceTitleVo.setAuditOpinion(invoice.getAuditOpinion());
                }*/
               UserInvoiceTitle userInvoiceTitle = userInvoiceTitleService.selectOne(filters);
               if (Objects.isNull(userInvoiceTitle)){

                   userInvoiceTitle = new UserInvoiceTitle();
                   userInvoiceTitle.setInvoiceStatus(HookahConstants.INVOICE_STATUS_0);
               }
                returnData.setData(userInvoiceTitle);
            }else{

                returnData.setData(userInvoiceTitleService.selectList(filters));
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return  returnData;
    }

    @RequestMapping(value = "/save")
    public ReturnData save(String userInvoiceTitle) {

        UserInvoiceTitle obj = JSON.parseObject(userInvoiceTitle, UserInvoiceTitle.class);
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {

            obj.setUserId(this.getCurrentUser().getUserId());
            userInvoiceTitleService.insert(obj);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value = "/del")
    public ReturnData delete(String titleId) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            UserInvoiceTitle userInvoiceTitle = new UserInvoiceTitle();
            userInvoiceTitle.setTitleId(titleId);
            userInvoiceTitle.setDeleteStatus(HookahConstants.DELETE_STATUS_0);
            userInvoiceTitleService.updateByIdSelective(userInvoiceTitle);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public ReturnData editUserInvoiceTitle(String userInvoiceTitle) {

        UserInvoiceTitle obj = JSON.parseObject(userInvoiceTitle, UserInvoiceTitle.class);
        // 重新添加 修改状态改为 已申请(审核中)
        obj.setInvoiceStatus(HookahConstants.INVOICE_STATUS_1);
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            userInvoiceTitleService.updateByIdSelective(obj);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 根据titleId查询单位抬头信息
     *
     * @param titleId
     * @return
     */
    @RequestMapping("/findById")
    @ResponseBody
    public ReturnData findUserInvoiceTitleInfoById(String titleId) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(userInvoiceTitleService.selectById(titleId));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


}
