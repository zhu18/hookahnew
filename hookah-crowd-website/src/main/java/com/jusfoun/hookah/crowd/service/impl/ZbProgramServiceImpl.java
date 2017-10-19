package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbProgramMapper;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementApplyMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.*;
import com.jusfoun.hookah.core.domain.zb.vo.ZbProgramVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by ctp on 2017/9/20.
 */
@Service
public class ZbProgramServiceImpl extends GenericServiceImpl<ZbProgram, Long> implements ZbProgramService{

    private static final Logger logger = LoggerFactory.getLogger(ZbProgramServiceImpl.class);

    private static final Short program_status_defaule = 0;

    @Resource
    private ZbRequireService zbRequireService;

    @Resource
    private ZbProgramMapper zbProgramMapper;

    @Resource
    ZbAnnexService zbAnnexService;

    @Resource
    ZbCommentService zbCommentService;

    @Resource
    MgZbRequireStatusService mgZbRequireStatusService;

    @Resource
    ZbRequirementApplyMapper zbRequirementApplyMapper;

    @Resource
    ZbRequireApplyWebsiteService zbRequireApplyWebsiteService;

    @Resource
    public void setDao(ZbProgramMapper ZbProgramMapper) {
        super.setDao(ZbProgramMapper);
    }

    @Override
    public ReturnData updataStatus(Long progId, Short status) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {
            //参数校验
            if(Objects.isNull(progId) || Objects.isNull(status)){
                returnData.setCode(ExceptionConst.AssertFailed);
                returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
                return returnData;
            }
            ZbProgram zbProgram = new ZbProgram();
            zbProgram.setId(progId);
            zbProgram.setStatus(status);
            this.updateByIdSelective(zbProgram);
            returnData.setMessage("修改方案ID为" + progId + "的需求提交方案状态成功@");
            logger.info("修改方案ID为" + progId + "的需求提交方案状态成功@");
        } catch (Exception e) {
            logger.error("修改方案ID为" + progId + "的需求提交方案状态失败@");
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setData(ExceptionConst.get(ExceptionConst.Error));
        }
        return returnData;
    }

    @Override
    public ReturnData selectProgramByReqId(Long reqId) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {

            //参数校验
            if(Objects.isNull(reqId)){
                returnData.setCode(ExceptionConst.AssertFailed);
                returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
                return returnData;
            }

            String userId = this.getCurrentUser().getUserId();
            //查询方案详情
            List<Condition> progFilters = new ArrayList<>();
            progFilters.add(Condition.eq("requirementId",reqId));
            progFilters.add(Condition.eq("userId",userId));
            ZbProgram zbProgram =  this.selectOne(progFilters);

            if(null != zbProgram){
                ZbProgramVo zbProgramVo = new ZbProgramVo();
                BeanUtils.copyProperties(zbProgram,zbProgramVo);

                //查看附件
                List<Condition> filters = new ArrayList<>();
                filters.add(Condition.eq("correlationId",zbProgram.getId()));
                filters.add(Condition.eq("type",ZbContants.ZB_ANNEX_TYPE_PROGRAM));
                List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filters);
                zbProgramVo.setZbAnnexes(zbAnnexes);
                returnData.setData(zbProgramVo);
                logger.info("@查询方案[id:" + zbProgram.getId() + "]" + zbProgramVo.getTitle() + "成功@");
            } else {
                returnData.setData(null);
                logger.info("@查询需求[id:" + reqId + "]方案为空@");
            }
        } catch (Exception e) {
            logger.error("@查询需求[id:" + reqId + "]方案失败@");
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setData(ExceptionConst.get(ExceptionConst.Error));
        }
        return returnData;
    }
    /** 服务商添加需求方评价 **/
    @Override
    @Transactional
    public ReturnData addRequirementComment(ZbComment zbComment) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {
            //参数校验
            if(Objects.isNull(zbComment) || null == zbComment.getProgramId() || null==zbComment.getRequirementId()){
                returnData.setCode(ExceptionConst.AssertFailed);
                returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
                return returnData;
            }

            User user = this.getCurrentUser();
            zbComment.setAddTime(new Date());
            zbComment.setStatus(ZbContants.Comment_Status.WAIT_CHECK.getCode());
            zbComment.setUserType(1);//1需方2供方
            zbComment.setUserId(user == null?"":user.getUserId());
            zbCommentService.insert(zbComment);

            //修改评价服务商评价需求方时间
            ZbRequirement zbRequirement = zbRequireService.selectById(zbComment.getRequirementId());
            if(Objects.nonNull(zbRequirement)){
                mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(),ZbContants.REQUIRECOMMENTTIME, DateUtils.getCurrentTimeFormat(new Date()));
            }

            //修改报名表状态(交易完成)
            List<Condition> filters = new ArrayList<Condition>();
            filters.add(Condition.eq("requirementId",zbComment.getRequirementId()));
            filters.add(Condition.eq("userId",user.getUserId()));
            ZbRequirementApply zbRequirementApply = new ZbRequirementApply();
            zbRequirementApply.setStatus(Integer.valueOf(ZbContants.ZbRequireMentApplyStatus.DEAL_SUCCESS.getCode()).shortValue());//记得改成常量
            zbRequireApplyWebsiteService.updateByCondition(zbRequirementApply,filters);

            returnData.setData(zbComment);
        } catch (Exception e) {
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setData(ExceptionConst.get(ExceptionConst.Error));
        }
        return returnData;
    }

    /**保存方案(提交&重新提交)**/
    @Override
    @Transactional
    public ReturnData save(ZbProgramVo zbProgramVo) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        //参数校验
        if(Objects.isNull(zbProgramVo) || Objects.isNull(zbProgramVo.getRequirementId()) || Objects.isNull(zbProgramVo.getApplyId())){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }

        String username = null;
        try {
            User user = this.getCurrentUser();

            List<Condition> filters = new ArrayList<Condition>();
            filters.add(Condition.eq("applyId",zbProgramVo.getApplyId()));
            filters.add(Condition.eq("requirementId",zbProgramVo.getRequirementId()));
            filters.add(Condition.eq("userId",user.getUserId()));
            List<ZbProgram> zbPrograms = this.selectList(filters);


            if(null != zbPrograms && zbPrograms.size() > 0){
                ZbProgram zbProgram = zbPrograms.get(0);
                //修改方案
                zbProgramVo.setId(zbProgram.getId());
                zbProgramVo.setStatus(program_status_defaule);//修改为默认状态
                zbProgramVo.setUserId(user.getUserId());
                zbProgramVo.setAddTime(new Date());//添加时间
                this.updateByIdSelective(zbProgramVo);

                //修改附件（先删除 后添加）
                filters.clear();
                filters.add(Condition.eq("correlationId",zbProgramVo.getId()));
                filters.add(Condition.eq("type",ZbContants.ZB_ANNEX_TYPE_PROGRAM));
                zbAnnexService.deleteByCondtion(filters);
            } else {
                username = user.getUserName();
                //设置方案默认值
                zbProgramVo.setAddTime(new Date());
                zbProgramVo.setStatus(ZbContants.Program_Status.DEFAULT.getCode());
                zbProgramVo.setUserId(user.getUserId());
                int zbId = zbProgramMapper.insertAndGetId(zbProgramVo);//创建方案
            }

            //添加附件
            List<ZbAnnex> zbAnnexes = zbProgramVo.getZbAnnexes();
            if(Objects.nonNull(zbAnnexes) && zbAnnexes.size() > 0){
                for(ZbAnnex zbAnnex : zbAnnexes){
                    zbAnnex.setAddTime(new Date());
                    zbAnnex.setCorrelationId(zbProgramVo.getId());
                    zbAnnex.setType(ZbContants.ZB_ANNEX_TYPE_PROGRAM);
                    zbAnnex.setStatus(ZbContants.Program_Status.DEFAULT.getCode());
                    zbAnnexService.insert(zbAnnex);
                }
            }

            //修改需求状态
            ZbRequirement zbRequirementStatus = new ZbRequirement();
            zbRequirementStatus.setId(zbProgramVo.getRequirementId());
            zbRequirementStatus.setStatus(ZbContants.Zb_Require_Status.WAIT_PLAT_YS.getCode().shortValue());

            //修改apply状态
            ZbRequirementApply zbRequirementApply = new ZbRequirementApply();
            zbRequirementApply.setId(zbProgramVo.getApplyId());
            zbRequirementApply.setStatus(Integer.valueOf(ZbContants.ZbRequireMentApplyStatus.REVIEW.getCode()).shortValue());//记得改成常量
            zbRequirementApplyMapper.updateByPrimaryKeySelective(zbRequirementApply);

            //保存方案提交时间
            ZbRequirement zbRequirement = zbRequireService.selectById(zbProgramVo.getRequirementId());
            if(Objects.nonNull(zbRequirement)){
                mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(),ZbContants.REQUIRECOMMENTTIME, DateUtils.getCurrentTimeFormat(new Date()));
            }

            returnData.setData(zbProgramVo);
            returnData.setMessage("@用户" + username + "向需求ID为" + zbProgramVo.getRequirementId() + "的需求保存方案成功@");
            logger.info("@用户" + username + "向需求ID为" + zbProgramVo.getRequirementId() + "的需求保存方案成功@");
        } catch (Exception e) {
            logger.error("@用户" + username + "向需求ID为" + zbProgramVo.getRequirementId() + "的需求保存方案失败@");
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setData(ExceptionConst.get(ExceptionConst.Error));
        }

        return returnData;
    }
}
