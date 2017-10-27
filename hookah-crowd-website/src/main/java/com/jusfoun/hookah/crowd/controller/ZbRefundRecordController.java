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
    public ReturnData goRefund(
            String userId,
            String requirementId,
            Double refundAmount,
            String bankCardNum,
            String desc,
            String payTime,
            String bankName
    ) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {

            ZbRefundRecord zbRefundRecord = new ZbRefundRecord();
            zbRefundRecord.setUserId(userId);
            zbRefundRecord.setRequirementId(Long.parseLong(requirementId));
            zbRefundRecord.setRefundAmount(new Double(refundAmount * 100).longValue());
            zbRefundRecord.setBankCardNum(bankCardNum);
            zbRefundRecord.setDescs(desc);
            zbRefundRecord.setPayTime(payTime);
            zbRefundRecord.setBankName(bankName);

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


    public static void main(String[] args){

        double refundAmount = 0;

        Long refundAmount2 = 300l;
        refundAmount = refundAmount2.doubleValue();

        Object data = (refundAmount == 0) ? refundAmount : (refundAmount / 10000);

        System.out.println(data);
    }

}
