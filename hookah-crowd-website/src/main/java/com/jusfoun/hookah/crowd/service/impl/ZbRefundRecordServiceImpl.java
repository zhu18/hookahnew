package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.zb.ZbRefundRecordMapper;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.domain.zb.ZbRefundRecord;
import com.jusfoun.hookah.core.domain.zb.ZbTrusteeRecord;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.service.PayBankCardService;
import com.jusfoun.hookah.crowd.service.ZbRefundRecordService;
import com.jusfoun.hookah.crowd.service.ZbTrusteeRecordService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZbRefundRecordServiceImpl extends GenericServiceImpl<ZbRefundRecord, Long> implements ZbRefundRecordService {

    @Resource
    private ZbRefundRecordMapper zbRefundRecordMapper;

    @Resource
    public void setDao(ZbRefundRecordMapper zbRefundRecordMapper) {
        super.setDao(zbRefundRecordMapper);
    }

    @Resource
    PayBankCardService payBankCardService;

    @Resource
    ZbTrusteeRecordService zbTrusteeRecordService;


    @Override
    public ZbRefundRecord insertRecord(Long requirementId, Integer refundType, String refundDesc) {

//        ZbRefundRecord zbRefundRecord = new ZbRefundRecord();
//        zbRefundRecord.setType(re);

        return null;
    }

    @Override
    public ReturnData selectRefundInfo(String userId, String requirementId) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {

            if(!StringUtils.isNotBlank(userId) || !StringUtils.isNotBlank(requirementId)){
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("查询参数有误^_^");
                return returnData;
            }

            // 查询银行开卡信息
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("userId", userId));
            filters.add(Condition.eq("bindFlag", 0));
            PayBankCard payBankCard = payBankCardService.selectOne(filters);
            if(payBankCard == null){
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("该用户未绑定银行卡^_^");
                return returnData;
            }

            returnData.setData(payBankCard);

            long refundAmount = 0;

            //查询退款金额信息
            List<Condition> filters2 = new ArrayList<>();
            filters2.add(Condition.eq("userId", userId));
            filters2.add(Condition.eq("requirementId", requirementId));
            filters2.add(Condition.eq("status", 1));
            List<ZbTrusteeRecord> list = zbTrusteeRecordService.selectList(filters2);
            if(list == null || list.size() <= 0){
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("未查询到退款信息^_^");
                return returnData;
            }

            for(ZbTrusteeRecord zbTrusteeRecord : list){
                refundAmount += zbTrusteeRecord.getActualMoney();
            }

            returnData.setData2(refundAmount / 100);

        }catch (Exception e){
            logger.error("众包去退款查询用户信息异常{}", e);
        }

        return returnData;
    }


    @Override
    public int insertData(ZbRefundRecord zbRefundRecord) {
        return zbRefundRecordMapper.insert(zbRefundRecord);
    }
}
