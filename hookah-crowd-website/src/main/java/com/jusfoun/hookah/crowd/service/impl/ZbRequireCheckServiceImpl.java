package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbRequirementCheckMapper;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementCheck;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.ZbRequireCheckService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/19.
 */
public class ZbRequireCheckServiceImpl extends GenericServiceImpl<ZbRequirementCheck, Long> implements ZbRequireCheckService {
    private ZbRequirementCheckMapper zbRequirementCheckMapper;

    ZbRequirementMapper zbRequirementMapper;

    @Resource
    public void setDao(ZbRequirementCheckMapper zbRequirementCheckMapper) {
        super.setDao(zbRequirementCheckMapper);
    }

    @Override
    public ReturnData<ZbRequirementCheck> requirementCheck(ZbRequirementCheck zbRequirementCheck) {
        List<Condition> filters = new ArrayList();
        ReturnData returnData = new ReturnData<>();
        ZbRequirement zbRequirement = new ZbRequirement();
        if (zbRequirementCheck.getRequirementId()!=null) {
            zbRequirement.setId(zbRequirementCheck.getRequirementId());
        }
        filters.add(Condition.eq(("requirementId"),zbRequirementCheck.getRequirementId()));

        try {
            returnData.setData(updateByCondition(zbRequirementCheck,filters));

            if (zbRequirementCheck.getCheckStatus().equals(1)){
               zbRequirement.setStatus(ZbContants.Zb_Require_Status.SINGING.getCode().shortValue());
             }
             if (zbRequirementCheck.getCheckStatus().equals(0)){
                zbRequirement.setStatus(ZbContants.Zb_Require_Status.WAIT_CHECK.getCode().shortValue());
            }
            if (zbRequirementCheck.getCheckStatus().equals(2)){
                zbRequirement.setStatus(ZbContants.Zb_Require_Status.WAIT_TG.getCode().shortValue());
            }
            zbRequirementMapper.updateByPrimaryKey(zbRequirement);

        }catch (Exception e){
            return ReturnData.error("发生了一个错误");
        }
        return returnData;
    }



}
