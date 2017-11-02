package com.jusfoun.hookah.crowd.service.impl;

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
import com.jusfoun.hookah.crowd.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    MgZbRequireStatusService mgZbRequireStatusService;

    @Resource
    ZbRequireApplyWebsiteService zbRequireApplyWebsiteService;

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

            BigDecimal refundAmount = new BigDecimal(0);

            ZbRequirement zbRequirement = zbRequireService.selectById(Long.parseLong(requirementId));
            if(zbRequirement == null){
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("未查询到相关需求^_^");
                return returnData;
            }else {

                if(zbRequirement.getStatus().equals(Short.parseShort("11"))){

                    // 查询服务商银行卡信息  去查询报名表userID信息
                    List<Condition> filters = new ArrayList<>();
                    filters.add(Condition.eq("requirementId", requirementId));
                    filters.add(Condition.eq("status", 5));
                    ZbRequirementApply zbRequireApply = zbRequireApplyService.selectOne(filters);
                    if(zbRequireApply == null){
                        returnData.setCode(ExceptionConst.Error);
                        returnData.setMessage("未查询到相关服务商报名信息^_^");
                        return returnData;
                    }

                    // 查询银行开卡信息
                    List<Condition> filters3 = new ArrayList<>();
                    filters3.add(Condition.eq("userId", zbRequireApply.getUserId()));
                    filters3.add(Condition.eq("bindFlag", 0));
                    PayBankCard payBankCard = payBankCardService.selectOne(filters3);
                    if(payBankCard == null){
                        returnData.setCode(ExceptionConst.Error);
                        returnData.setMessage("该用户未绑定银行卡^_^");
                        return returnData;
                    }

                    returnData.setData(payBankCard);

//                    refundAmount = zbRequirement.getRewardMoney().doubleValue() / 100;
                    refundAmount = refundAmount.add(new BigDecimal(zbRequirement.getRewardMoney().doubleValue() / 100));

                }else {

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
//                        refundAmount += zbTrusteeRecord.getActualMoney().doubleValue();
                        refundAmount = refundAmount.add(new BigDecimal(zbTrusteeRecord.getActualMoney().doubleValue() / 10000));
                    }

//                    refundAmount = (refundAmount == 0) ? refundAmount : (refundAmount / 10000);

                }
            }

            returnData.setData2(refundAmount);

        }catch (Exception e){
            logger.error("众包去退款查询用户信息异常{}", e);
        }

        return returnData;
    }


    @Transactional
    public ReturnData insertData(ZbRefundRecord zbRefundRecord) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {

            int flag = 0;

            if(zbRefundRecord.getUserId() == null || zbRefundRecord.getRequirementId() == null){

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

            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("requirementId", zbRefundRecord.getRequirementId()));
            filters.add(Condition.ne("status", 2));
            ZbRequirementApply apply = zbRequireApplyService.selectOne(filters);

            if(zbRequirement.getStatus().equals(Short.parseShort("11"))){
                // 平台付款给
                zbRefundRecord.setType(ZbContants.PlatPayType.PAY_TO_PROVIDER.getCode());
            }else if(zbRequirement.getStatus().equals(Short.parseShort("16"))){


                if(apply == null){//未查询到相关需求的报名数据  流标
                    zbRefundRecord.setType(ZbContants.PlatPayType.FAIL_TO_SOLD.getCode());
                    flag = 1;
                }else {
                    if(apply.getStatus().equals(ZbContants.ZbRequireMentApplyStatus.DEAL_CANCE.getCode().shortValue())){
                        //驳回失败
                        zbRefundRecord.setType(ZbContants.PlatPayType.BREA_FAILE.getCode());
                        flag = 1;
                    }else if(apply.getStatus().equals(ZbContants.ZbRequireMentApplyStatus.DEAL_RENEGE_FAIL.getCode().shortValue())){
                        // 违约到期
                        zbRefundRecord.setType(ZbContants.PlatPayType.DATE_EXPIRE.getCode());
                        flag = 1;
                    }else{
                        returnData.setCode(ExceptionConst.Error);
                        returnData.setMessage("业务数据有误，请检查^_^");
                        return returnData;
                    }
                }
            }else {
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("业务数据有误，请检查^_^");
                return returnData;
            }

            zbRefundRecord.setAddTime(new Date());
            zbRefundRecord.setStatus(1);

            int n = zbRefundRecordMapper.insert(zbRefundRecord);
            if(n == 1){
                ZbRequirement changeStatus = new ZbRequirement();
                changeStatus.setId(zbRefundRecord.getRequirementId());
                if(flag == 1){ //标识为流标状态 违约到期 驳回  直接改为交易取消

                    changeStatus.setStatus(ZbContants.Zb_Require_Status.ZB_CANCEL.getCode().shortValue());

                    mgZbRequireStatusService.
                            setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.CANCELTIME, DateUtil.getSimpleDate(new Date()));

                }else {// 付款改成评价

                    //修改报名状态为待评价
                    zbRequireApplyWebsiteService.updateStatus(apply == null ? null : apply.getId(), ZbContants.ZbRequireMentApplyStatus.COMMENT_ING.getCode());

                    changeStatus.setStatus(ZbContants.Zb_Require_Status.WAIT_PJ.getCode().shortValue());

                    mgZbRequireStatusService.
                            setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.PAYTIME, DateUtil.getSimpleDate(new Date()));
                }
                int m = zbRequireService.updateByIdSelective(changeStatus);

                if(m == 1){
                    logger.info("付款/退款状态修改成功^_^------{}", m);
                }else{
                    logger.error("付款/退款状态修改失败^_^------{}", m);
                    throw new RuntimeException();
                }
            }else{
                throw new RuntimeException();
            }

            returnData.setMessage("线下转账确认操作完成^_^");

        }catch (Exception e){
            logger.error("线下确认付款/确认退款操作异常{}", e);
        }

        return returnData;
    }

    public static void main(String[] args){

        Double d1 = 0.1;
        Double d2 = 0.02;
        System.out.println(d1 + d2);
    }

}
