package com.jusfoun.hookah.webiste.controller;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.core.domain.UserInvoiceTitle;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.UserInvoiceTitleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gring on 2017/11/27.
 */
@RestController
@RequestMapping(value = "/api/userInvoiceTitle")
public class UserInvoiceTitleController extends BaseController {

    @Resource
    UserInvoiceTitleService userInvoiceTitleService;
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
            returnData.setData(userInvoiceTitleService.selectList(filters));
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
            userInvoiceTitleService.delete(titleId);
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