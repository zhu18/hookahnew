package com.jusfoun.hookah.crowd.controller;


import com.jusfoun.hookah.core.domain.zb.ZbRefundRecord;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbRefundRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ZbRefundRecordController {

    private static Logger logger = LoggerFactory.getLogger(ZbRefundRecordController.class);

    @Resource
    ZbRefundRecordService zbRefundRecordService;

    @RequestMapping(value = "/api/refund/goRefund")
    @ResponseBody
    public ReturnData goRefund(ZbRefundRecord zbRefundRecord) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {

            returnData = zbRefundRecordService.insertData(zbRefundRecord);
        }catch (Exception e){
            logger.error("确认付款/确认退款操作异常{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("确认付款/确认退款操作异常");
            return returnData;
        }

        return returnData;
    }

    @RequestMapping(value = "/api/refund/selectRefundInfo")
    @ResponseBody
    public ReturnData selectBankInfo(String userId, String requiredmentId) {

        //根据待退款的用户ID，获取绑定的银行卡信息
        return zbRefundRecordService.selectRefundInfo(userId, requiredmentId);
    }

}
