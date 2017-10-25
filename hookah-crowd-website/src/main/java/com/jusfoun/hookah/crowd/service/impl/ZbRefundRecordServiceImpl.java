package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.zb.ZbRefundRecordMapper;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.domain.zb.ZbRefundRecord;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.domain.zb.ZbTrusteeRecord;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    ZbRequireService zbRequireService;

    @Resource
    ZbRequireApplyService zbRequireApplyService;


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
    public ReturnData insertData(ZbRefundRecord zbRefundRecord) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {

            if(zbRefundRecord.getId() == null || zbRefundRecord.getRequirementId() == null){

                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("查询参数有误^_^");
                return returnData;
            }

            ZbRequirement zbRequirement = zbRequireService.selectById(zbRefundRecord.getRequirementId());
            if(zbRequirement == null){
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("未查询到相关需求^_^");
                return returnData;
            }

            if(zbRequirement.getStatus().equals(Short.parseShort("11"))){
                // 平台付款给
                zbRefundRecord.setType(ZbContants.PlatPayType.DATE_EXPIRE.getCode());
            }else{

                List<Condition> filters = new ArrayList<>();
                filters.add(Condition.eq("requirementId", zbRefundRecord.getRequirementId()));
                ZbRequirementApply apply = zbRequireApplyService.selectOne(filters);
                if(apply == null){//未查询到相关需求的报名数据  流标
                    zbRefundRecord.setType(ZbContants.PlatPayType.FAIL_TO_SOLD.getCode());
                }else {
                    if(apply.getStatus().equals(ZbContants.ZbRequireMentApplyStatus.DEAL_CANCE.getCode())){
                        //驳回失败
                        zbRefundRecord.setType(ZbContants.PlatPayType.BREA_FAILE.getCode());
                    }else if(apply.getStatus().equals(ZbContants.ZbRequireMentApplyStatus.DEAL_RENEGE_FAIL.getCode())){
                        // 违约到期
                        zbRefundRecord.setType(ZbContants.PlatPayType.DATE_EXPIRE.getCode());
                    }else{
                        returnData.setCode(ExceptionConst.Error);
                        returnData.setMessage("业务数据有误，请检查^_^");
                        return returnData;
                    }
                }
            }

            zbRefundRecord.setAddTime(new Date());
            zbRefundRecord.setStatus(1);

            int n = zbRefundRecordMapper.insert(zbRefundRecord);
            if(n == 1){
                ZbRequirement changeStatus = new ZbRequirement();
                changeStatus.setId(zbRefundRecord.getRequirementId());
                changeStatus.setStatus(ZbContants.Zb_Require_Status.WAIT_PJ.getCode().shortValue());
                int m = zbRequireService.updateByIdSelective(changeStatus);
            }

            returnData.setMessage("线下转账确认操作完成^_^");

        }catch (Exception e){
            logger.error("线下确认付款/确认退款操作异常{}", e);
        }

        return returnData;

    }
}
