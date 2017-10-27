package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbRequirementApplyMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.ZbProgram;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRequirementApplyVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.MgZbRequireStatusService;
import com.jusfoun.hookah.crowd.service.UserService;
import com.jusfoun.hookah.crowd.service.ZbRequireApplyWebsiteService;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import com.jusfoun.hookah.crowd.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ZbRequireService zbRequireService;

    @Resource
    UserService userService;

    @Resource
    private MgZbRequireStatusService mgZbRequireStatusService;

    @Resource
    private ZbRequireApplyWebsiteService zbRequireApplyWebsiteService;

    @Resource
    public void setDao(ZbRequirementApplyMapper zbRequirementApplyMapper) {
        super.setDao(zbRequirementApplyMapper);
    }

    @Override
    @Transactional
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
            Long reqId = zbRequirementApply.getRequirementId();
            ZbRequirement zbRequirement = zbRequireService.selectById(reqId);
            String zbReqId = zbRequirement.getUserId();//需求Id
            User user = this.getCurrentUser();
            String currUserId = user == null ? "" : user.getUserId();//当前用户Id

            if(Objects.nonNull(zbRequirement)){

                Date date = zbRequirement.getApplyDeadline();//报名截止时间
                if(Objects.nonNull(date)){
                    //判断是否截止报名
                    Long deadLineTimeLong = date.getTime();
                    Long currTimeLong = System.currentTimeMillis();

                    if(deadLineTimeLong < currTimeLong){
                        returnData.setCode(ExceptionConst.Error);
                        returnData.setMessage("该需求报名已截止");
                        return returnData;
                    }
                }

                //用户是否已报名
                List<Condition> filters = new ArrayList<>();
                filters.add(Condition.eq("requirementId" , reqId));
                filters.add(Condition.eq("userId" , currUserId));
                List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyWebsiteService.selectList(filters);
                if(null != zbRequirementApplies && zbRequirementApplies.size() > 0){
                    returnData.setCode(ExceptionConst.Error);
                    returnData.setMessage("您已报名");
                    return returnData;
                }

                //判断需求的userId和当前用户Id是否相等
                if(currUserId.equals(zbReqId)){
                    returnData.setCode(ExceptionConst.Error);
                    returnData.setMessage("该需求为自己发布的需求，无法报名");
                } else {
                    zbRequirementApply.setAddTime(new Date());
                    zbRequirementApply.setUserId(currUserId);
                    zbRequirementApply.setStatus(Short.valueOf("0"));//默认已报名
                    zbRequirementApplyMapper.insertAndGetId(zbRequirementApply);
                    returnData.setData(buildZbRequirementApplyVo(zbRequirementApply,user.getUserId()));
                    logger.info("@用户" +user==null?"":user.getUserName()+ "报名需求ID为" + zbRequirementApply.getRequirementId() + "的需求成功@");

                    //插入报名时间
                    mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.APPLYTIME, DateUtils.toDefaultNowTime());
                }
            } else {
                returnData.setCode(ExceptionConst.Error);
                returnData.setMessage("该需求不存在");
                logger.info("@用户" +user==null?"":user.getUserName()+ "报名需求【id:" + reqId + "】不存在");
            }
        } catch (Exception e) {
            logger.error("@用户报名需求ID为" + zbRequirementApply.getRequirementId() + "的需求失败@");
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.Error));
            returnData.setData(e.getMessage());
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
            User user = this.getCurrentUser();
            String userId = user.getUserId();
            //查询报名详情
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("requirementId",reqId));
            filters.add(Condition.eq("userId",userId));
            ZbRequirementApply zbRequirementApply = this.selectOne(filters);
            returnData.setData(buildZbRequirementApplyVo(zbRequirementApply,userId));
            if(null != zbRequirementApply){
                logger.info("@查询报名[id:" + zbRequirementApply.getId() + "]成功@");
            } else {
                logger.info("该用户没有报名");
            }
        } catch (Exception e) {
            logger.error("@查询需求[id:" + reqId + "]报名信息失败@");
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setData(ExceptionConst.get(ExceptionConst.Error));
        }
        return returnData;
    }

    @Override
    public ReturnData updateStatus(Long applyId, Integer status) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {

            if(Objects.isNull(applyId) || Objects.isNull(status)){
                returnData.setCode(ExceptionConst.AssertFailed);
                returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
                return returnData;
            }

            ZbRequirementApply zbRequirementApply = new ZbRequirementApply();
            zbRequirementApply.setId(applyId);
            zbRequirementApply.setStatus(status.shortValue());
            this.updateByIdSelective(zbRequirementApply);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            e.printStackTrace();
        }
        return returnData;
    }

    //封装报名信息详情
    private ZbRequirementApplyVo buildZbRequirementApplyVo(ZbRequirementApply zbRequirementApply,String userId){
        ZbRequirementApplyVo zbRequirementApplyVo = new ZbRequirementApplyVo();
        if(Objects.nonNull(zbRequirementApply)){
            BeanUtils.copyProperties(zbRequirementApply,zbRequirementApplyVo);
            User user = userService.selectById(userId);
            zbRequirementApplyVo.setCurrUserPhone(user.getMobile());
            zbRequirementApplyVo.setMobile(user.getMobile());
            if(null != zbRequirementApply.getRequirementId()){
                List<Condition> filters = new ArrayList<Condition>();
                filters.add(Condition.eq("requirementId",zbRequirementApply.getRequirementId()));
                List<ZbRequirementApply> zbRequirementApplies = this.selectList(filters);
                zbRequirementApplyVo.setApplyNumber(zbRequirementApplies == null ? 0 : zbRequirementApplies.size());
            } else {
                zbRequirementApplyVo.setApplyNumber(0);
            }
        } else {
            zbRequirementApplyVo = null;
        }
        return zbRequirementApplyVo;
    }

}
