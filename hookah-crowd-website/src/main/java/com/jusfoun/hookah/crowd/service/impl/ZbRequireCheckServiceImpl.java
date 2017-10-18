package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbRequirementCheckMapper;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementCheck;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.MgZbRequireStatusService;
import com.jusfoun.hookah.crowd.service.ZbRequireCheckService;
import com.jusfoun.hookah.crowd.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by admin on 2017/9/19.
 */
@Service
public class ZbRequireCheckServiceImpl extends GenericServiceImpl<ZbRequirementCheck, Long> implements ZbRequireCheckService {

    @Resource
    private ZbRequirementCheckMapper zbRequirementCheckMapper;

    @Resource
    MgZbRequireStatusService mgZbRequireStatusService;

    @Resource
    ZbRequirementMapper zbRequirementMapper;

    @Resource
    public void setDao(ZbRequirementCheckMapper zbRequirementCheckMapper) {
        super.setDao(zbRequirementCheckMapper);
    }

    @Override
    public ReturnData<ZbRequirementCheck> requirementCheck(ZbRequirementCheck zbRequirementCheck, User user) {
        ReturnData returnData = new ReturnData<>();
        ZbRequirement zbRequirement = new ZbRequirement();
        ZbRequirement zbRequirement1= zbRequirementMapper.selectByPrimaryKey(zbRequirementCheck.getRequirementId());

        if (zbRequirementCheck.getRequirementId()!=null) {
            zbRequirement.setId(zbRequirementCheck.getRequirementId());
        }
        if (zbRequirement1.getRequireSn()!=null){
            zbRequirement.setRequireSn(zbRequirement1.getRequireSn());
        }
        zbRequirementCheck.setCheckContent(zbRequirementCheck.getCheckContent());
        zbRequirementCheck.setRequirementId(zbRequirementCheck.getRequirementId());
        zbRequirementCheck.setCheckStatus(zbRequirementCheck.getCheckStatus());
        zbRequirementCheck.setCheckTime(new Date());
        zbRequirementCheck.setCheckUser(user.getUserName());
        try {
            returnData.setData(insert(zbRequirementCheck));

            if (null != zbRequirementCheck.getCheckStatus() && Short.valueOf("1").equals(zbRequirementCheck.getCheckStatus())){
               zbRequirement.setStatus(ZbContants.Zb_Require_Status.WAIT_TG.getCode().shortValue());
             }
             if (null != zbRequirementCheck.getCheckStatus() && Short.valueOf("0").equals(zbRequirementCheck.getCheckStatus())){
                zbRequirement.setStatus(ZbContants.Zb_Require_Status.WAIT_CHECK.getCode().shortValue());
            }
            if (null != zbRequirementCheck.getCheckStatus() && Short.valueOf("2").equals(zbRequirementCheck.getCheckStatus())){
                zbRequirement.setStatus(ZbContants.Zb_Require_Status.CHECK_NOT.getCode().shortValue());
            }
            //添加平台审核时间
            if (zbRequirement.getRequireSn()!=null){
                mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.CHECKTIME, DateUtil.getSimpleDate(zbRequirementCheck.getCheckTime()));
            }

            zbRequirementMapper.updateByPrimaryKeySelective(zbRequirement);

        }catch (Exception e){
            e.printStackTrace();
            return ReturnData.error("发生了一个错误");
        }
        return returnData;
    }



}
