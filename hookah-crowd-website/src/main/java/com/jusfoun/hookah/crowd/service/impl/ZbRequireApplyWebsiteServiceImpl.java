package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbRequirementApplyMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.ZbProgram;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbRequireApplyWebsiteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by ctp on 2017/9/26.
 */
@Service
public class ZbRequireApplyWebsiteServiceImpl extends GenericServiceImpl<ZbRequirementApply, Long> implements ZbRequireApplyWebsiteService {

    private static final Logger logger = LoggerFactory.getLogger(ZbRequireApplyWebsiteServiceImpl.class);

    @Resource
    private ZbRequirementApplyMapper zbRequirementApplyMapper;

    @Resource
    public void setDao(ZbRequirementApplyMapper zbRequirementApplyMapper) {
        super.setDao(zbRequirementApplyMapper);
    }

    @Override
    public ReturnData addApplay(ZbRequirementApply zbRequirementApply) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        //参数校验
        if(Objects.isNull(zbRequirementApply) || Objects.isNull(zbRequirementApply.getRequirementId())){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        try {
            User user = this.getCurrentUser();
            zbRequirementApply.setAddTime(new Date());
            zbRequirementApply.setUserId(user == null ? "" : user.getUserId());
            zbRequirementApply.setStatus(Short.valueOf("0"));//默认已报名
            zbRequirementApplyMapper.insertAndGetId(zbRequirementApply);
            logger.info("@用户" +user==null?"":user.getUserName()+ "报名需求ID为" + zbRequirementApply.getRequirementId() + "的需求成功@");
        } catch (Exception e) {
            logger.error("@用户报名需求ID为" + zbRequirementApply.getRequirementId() + "的需求失败@");
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setData(ExceptionConst.get(ExceptionConst.Error));
        }
        return returnData;
    }

    @Override
    public ReturnData selectByReqId(Long reqId) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        //参数校验
        if(Objects.isNull(reqId)){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }

        try {
            String userId = this.getCurrentUser().getUserId();
            //查询方案详情
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("requirementId",reqId));
            filters.add(Condition.eq("userId",userId));
            ZbRequirementApply zbRequirementApply = this.selectOne(filters);
            returnData.setData(zbRequirementApply);
            logger.info("@查询报名[id:" + zbRequirementApply.getId() + "]成功@");
        } catch (Exception e) {
            logger.error("@查询需求[id:" + reqId + "]报名信息失败@");
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setData(ExceptionConst.get(ExceptionConst.Error));
        }
        return returnData;
    }
}
