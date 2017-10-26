package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.domain.zb.ZbProgram;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.ZbProgramService;
import com.jusfoun.hookah.crowd.service.ZbRequireApplyWebsiteService;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import com.jusfoun.hookah.crowd.service.ZbRequirementTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by ctp on 2017/10/25.
 */
@Service
public class ZbRequirementTaskServiceImpl implements ZbRequirementTaskService {

    private final static Logger logger = LoggerFactory.getLogger(ZbRequirementTaskServiceImpl.class);

    @Resource
    private ZbProgramService zbProgramService;

    @Resource
    private ZbRequireApplyWebsiteService zbRequireApplyWebsiteService;

    @Resource
    private ZbRequireService zbRequireService;

    @Override
    @Scheduled(cron="0 0 0 * * ?")
    public void flushRequireRenegeStatusTask() {
        flushStatus();
    }


    private void flushStatus(){

        logger.info("------------------刷新违约状态任务开始----------------------");

        //需求报名用户到期方案未提交 标记为违约失败
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("status", ZbContants.ZbRequireMentApplyStatus.WORKING.getCode()));

        List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyWebsiteService.selectList(filters);
        if(null != zbRequirementApplies && zbRequirementApplies.size() > 0){
            for (ZbRequirementApply zbRequirementApply : zbRequirementApplies) {
                Long reqId = zbRequirementApply.getRequirementId();//需求Id
                Long applyId = zbRequirementApply.getId();//报名Id
                String userId = zbRequirementApply.getUserId();//用户Id

                ZbRequirement zbRequirement = zbRequireService.selectById(reqId);
                if(Objects.nonNull(zbRequirement)){
                    Date delDeadline = zbRequirement.getDeliveryDeadline();
                    if(Objects.nonNull(delDeadline)){

                        Long delDeadlineLong = delDeadline.getTime();
                        Long currTimeLong  = System.currentTimeMillis();
                        //方案交互截止时间大于当前时间 视为违约失败
                        if(currTimeLong > delDeadlineLong){
                            filters.clear();
                            filters.add(Condition.eq("applyId", applyId ));
                            filters.add(Condition.eq("requirementId",reqId));
                            filters.add(Condition.eq("userId", userId));
                            ZbProgram zbProgram = zbProgramService.selectOne(filters);
                            if(Objects.isNull(zbProgram)){
                                //修改报名表信息为违约失败
                                ZbRequirementApply zbRequirementApply1 = new ZbRequirementApply();
                                zbRequirementApply1.setId(applyId);
                                zbRequirementApply1.setStatus(ZbContants.ZbRequireMentApplyStatus.DEAL_RENEGE_FAIL.getCode().shortValue());
                                zbRequireApplyWebsiteService.updateByIdSelective(zbRequirementApply1);

                                //修改需求表状态为退款中
                                ZbRequirement zbReq =  new ZbRequirement();
                                zbReq.setId(reqId);
                                zbReq.setStatus(ZbContants.Zb_Require_Status.WAIT_TK.getCode().shortValue());
                                zbRequireService.updateByIdSelective(zbReq);
                            }
                        }
                    }
                }
            }
        }
        logger.info("------------------刷新违约状态任务end----------------------");
    }


}
