package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.dao.zb.ZbTypeMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.*;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbRequireStatus;
import com.jusfoun.hookah.core.domain.zb.vo.*;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
import com.jusfoun.hookah.crowd.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ctp on 2017/10/10.
 */
@Service
public class ServiceProviderServiceImpl extends GenericServiceImpl<ZbRequirement, Long> implements ServiceProviderService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderServiceImpl.class);

    /**报名状态 1:已报名 0:未报名**/
    public static final Integer APPLY_STATUS_YES = 1;
    public static final Integer APPLY_STATUS_NO = 0;

    @Resource
    private ZbRequirementMapper zbRequirementMapper;

    @Resource
    private ZbRequireApplyWebsiteService zbRequireApplyWebsiteService;

    @Resource
    private ZbTypeMapper zbTypeMapper;

    @Resource
    private ZbProgramService zbProgramService;

    @Resource
    private ZbAnnexService zbAnnexService;

    @Resource
    MgZbRequireStatusService mgZbRequireStatusService;

    @Resource
    ZbCommentService zbCommentService;

    @Resource
    UserService userService;

    @Resource
    public void setDao(ZbRequirementMapper zbRequirementMapper) {
        super.setDao(zbRequirementMapper);
    }

    //服务商 - 需求大厅
    @Override
    public ReturnData getRequirementVoList(ZbRequirementPageHelper helper) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        if(Objects.isNull(helper)){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        try{
            if (helper.getPageNumber() == null) helper.setPageNumber(HookahConstants.PAGE_NUM);
            if (helper.getPageSize() == null) helper.setPageSize(HookahConstants.PAGE_SIZE);
            String timeType = helper.getTimeType();
            String pressTime = "";
            if (StringUtils.isNotBlank(timeType)) {
                switch (timeType) {
                    case ("day"):
                        pressTime = DateUtil.datePlusOrMinusAsString(-1);
                        break;
                    case ("week"):
                        pressTime = DateUtil.datePlusOrMinusAsString(-7);
                        break;
                    case ("month"):
                        pressTime = DateUtil.monthPlusOrMinusAsString(-1);
                        break;
                    case ("gtmonth"):
                        pressTime = DateUtil.monthPlusOrMinusAsString(-1);
                        break;
                    default:
                        pressTime = "";
                        break;
                }
            }

            //查询标题 发布时间排序
            List<Condition> filters = new ArrayList<>();
            if(Objects.nonNull(helper.getRequireTitle())){
                filters.add(Condition.like("title",helper.getRequireTitle()));
            }
            //需求类型
            if(Objects.nonNull(helper.getType())){
                filters.add(Condition.eq("type",helper.getType()));
            }
            if(Objects.nonNull(timeType) && !"".equals(timeType) &&
                    Objects.nonNull(pressTime) && !"".equals(pressTime)){
                if("gtmonth".equals(timeType)){
                    filters.add(Condition.lt("pressTime",pressTime));
                } else {
                    filters.add(Condition.le("pressTime",pressTime));
                }
            }
            filters.add(Condition.in("status",new Short[]{5,6}));
            List<OrderBy> orderBys = new ArrayList<>();
            if(!StringUtils.isNotBlank(helper.getOrder()) && !StringUtils.isNotBlank(helper.getSort())){
                orderBys.add(OrderBy.desc("applyDeadline"));
            } else {
                if("asc".equals(helper.getSort())){
                    orderBys.add(OrderBy.asc(helper.getOrder()));
                } else {
                    orderBys.add(OrderBy.desc(helper.getOrder()));
                }
            }
            Pagination pagination = this.getListInPage(helper.getPageNumber(),helper.getPageSize(),filters,orderBys);
            User user = this.getCurrentUser();
            pagination.setList(copyZbRequirementData(pagination.getList(),user.getUserId()));
            returnData.setData(pagination);
        } catch (Exception e) {
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setData(ExceptionConst.get(ExceptionConst.Error));
        }

        return returnData;
    }

    //服务商 - 我的任务
    @Override
    public ReturnData getRequirementVoListByUserId(ZbRequirementPageHelper helper) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        if(Objects.isNull(helper)){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        try{
            if (helper.getPageNumber() == null) helper.setPageNumber(HookahConstants.PAGE_NUM);
            if (helper.getPageSize() == null) helper.setPageSize(HookahConstants.PAGE_SIZE);
            //获取当前用户Id
            String userId = this.getCurrentUser() == null ? "" : this.getCurrentUser().getUserId();
            //获取当前用户已报名的需求id集合
            List<Condition> filters = new ArrayList<Condition>();
            if(Objects.nonNull(helper.getApplyStatus()) && !Short.valueOf("-1").equals(helper.getApplyStatus())){
                filters.add(Condition.eq("status", helper.getApplyStatus()));
            }
            filters.add(Condition.eq("userId",userId));
            List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyWebsiteService.selectList(filters);
            List<Long> zbRequirementIds = new ArrayList<>();
            zbRequirementIds.add(-1l);
            if(Objects.nonNull(zbRequirementApplies) && zbRequirementApplies.size() > 0){
                zbRequirementIds = zbRequirementApplies.stream().map(zbRequirementApply->{return zbRequirementApply.getRequirementId();}).collect(Collectors.toList());
            }
            filters.clear();
            filters.add(Condition.in("id", zbRequirementIds.toArray()));
            if(Objects.nonNull(helper.getRequireTitle())){
                filters.add(Condition.like("title",helper.getRequireTitle()));
            }
            if(Objects.nonNull(helper.getRequireSn())){
                filters.add(Condition.like("requireSn",helper.getRequireSn()));
            }
            List<OrderBy> orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("pressTime"));
            Pagination pagination = this.getListInPage(helper.getPageNumber(),helper.getPageSize(),filters,orderBys);
            pagination.setList(copyMyTaskZbRequirementData(pagination.getList(),zbRequirementApplies));
            returnData.setData(pagination);

        } catch (Exception e) {
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setData(ExceptionConst.get(ExceptionConst.Error));
        }

        return returnData;
    }

    @Override
    public ReturnData findByRequirementId(Long reqId) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        if(Objects.isNull(reqId)){
            returnData.setCode(ExceptionConst.AssertFailed);
            returnData.setMessage(ExceptionConst.get(ExceptionConst.AssertFailed));
            return returnData;
        }
        try{
            //从session获取用户信息
            Map userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
            //获取需求详情数据VO
            ZbRequirementSPVo zbRequirementSPVo = buildZbRequireSPVo(reqId);

            //需求不存在
            if(null == zbRequirementSPVo){
                returnData.setData(userMap==null ? zbRequirementSPVo : new ZbServiceProviderRequireVo());
                return returnData;
            }

            ZbServiceProviderRequireVo zbServiceProviderRequireVo = new ZbServiceProviderRequireVo();
            //判断是否登录
            if(null == userMap){
                //未登录
                zbRequirementSPVo.setAnnexIsOperate(0);//不可下载
                zbServiceProviderRequireVo.setZbRequirementSPVo(zbRequirementSPVo);
                zbServiceProviderRequireVo.setUserType(-1);//用户类型 未登录
                returnData.setData(zbServiceProviderRequireVo);
            } else {
                //已登录

                String userId = (String) userMap.get("userId");
                User user = userService.selectById(userId);
                //获取用户类型
                zbServiceProviderRequireVo.setUserType(user.getUserType());
                zbServiceProviderRequireVo.setZbRequirementSPVo(zbRequirementSPVo);//需求信息
                returnData.setData(loginRequirementDetail(reqId,zbServiceProviderRequireVo,zbRequirementSPVo));
            }

        } catch (Exception e) {
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setData(ExceptionConst.get(ExceptionConst.Error));
        }
        return returnData;
    }


    private ZbServiceProviderRequireVo loginRequirementDetail(Long reqId,ZbServiceProviderRequireVo zbServiceProviderRequireVo,ZbRequirement zbRequirement){

        List<Condition> filters = new ArrayList<>();
        //方案信息
        ZbProgramVo zbProgramVo = zbProgramService.selectProgramByReqId(reqId) == null ? new ZbProgramVo() : (ZbProgramVo) zbProgramService.selectProgramByReqId(reqId).getData();

        //报名信息
        ZbRequirementApplyVo zbRequirementApplyVo = zbRequireApplyWebsiteService.selectByReqId(reqId) == null ? new ZbRequirementApplyVo() : (ZbRequirementApplyVo) zbRequireApplyWebsiteService.selectByReqId(reqId).getData();

        //时间条信息
        MgZbRequireStatus mgZbRequireStatus = mgZbRequireStatusService.getByRequirementSn(zbRequirement.getRequireSn());

        //类似任务
        filters.clear();
        filters.add(Condition.eq("type",zbRequirement.getType()));
        filters.add(Condition.eq("status", ZbContants.Zb_Require_Status.SINGING.getCode().shortValue()));
        List<OrderBy> orderBys = new ArrayList<>();
        orderBys.add(OrderBy.desc("pressTime"));
        List<ZbRequirement> anTaskRequires = this.selectList(filters,orderBys);

        zbServiceProviderRequireVo.setZbProgramVo(zbProgramVo);//方案信息
        zbServiceProviderRequireVo.setZbRequirementApplyVo(zbRequirementApplyVo);//报名信息
        zbServiceProviderRequireVo.setMgZbRequireStatus(mgZbRequireStatus);//时间条信息
        zbServiceProviderRequireVo.setReqStatus(zbRequirementApplyVo == null ? -1 : zbRequirementApplyVo.getStatus());//当前用户的需求状态
        zbServiceProviderRequireVo.setZbCommentVo(buildZbCommentVo(zbProgramVo == null ? null : zbProgramVo.getId()));//评论信息
        zbServiceProviderRequireVo.setAnalogyTask(anTaskRequires);//类似任务
        return zbServiceProviderRequireVo;
    }

    //封装需求详情数据
    private ZbRequirementSPVo buildZbRequireSPVo(Long reqId){
        ZbRequirementSPVo zbRequirementSPVo = new ZbRequirementSPVo();
        //----需求信息 start----
        ZbRequirement zbRequirement = zbRequirementMapper.selectByPrimaryKey(reqId);

        //需求不存在
        if(null == zbRequirement){
            return null;
        }

        //需求类型
        Short typeId = zbRequirement.getType();
        ZbType zbType = zbTypeMapper.selectByPrimaryKey(typeId.intValue());
        zbRequirement.setTypeName(zbType == null? "未知类型" : zbType.getTypeName());

        List<Condition> filters = new ArrayList<>();
        if (zbRequirement.getId() != null) {
            filters.add(Condition.eq("correlationId", zbRequirement.getId()));
        }
        filters.add(Condition.eq("type", 0));
        //需求附件
        List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filters);
        BeanUtils.copyProperties(zbRequirement,zbRequirementSPVo);
        zbRequirementSPVo.setAnnex(zbAnnexes);


        //----需求信息 end----
        return zbRequirementSPVo;
    }

    //获取当前需求的评价
    private ZbCommentVo buildZbCommentVo(Long zbProgId){
        ZbCommentVo zbCommentVo = new ZbCommentVo();

        if(Objects.nonNull(zbProgId)){
            //对服务商的评价
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("programId", zbProgId));
            filters.add(Condition.eq("userType", 2));
            ZbComment servicerComment = zbCommentService.selectOne(filters);
            zbCommentVo.setServicerComment(servicerComment);

            //对需求方的评价
            filters.clear();
            filters.add(Condition.eq("programId", zbProgId));
            filters.add(Condition.eq("userType", 1));
            ZbComment requireComment = zbCommentService.selectOne(filters);
            zbCommentVo.setRequireZbComment(requireComment);
        }
        return zbCommentVo;
    }

    //服务商 - 需求大厅 - 封装数据
    private List<ZbRequirementSPVo> copyZbRequirementData(List<ZbRequirement> zbRequirements, String userId) throws Exception{
        List<ZbRequirementSPVo> zbRequirementSPVos = new ArrayList<>();
        for(ZbRequirement zbRequirement : zbRequirements){
            ZbRequirementSPVo zbRequirementVo = new ZbRequirementSPVo();
            //计算报名截止剩余时间
            Date deadline = zbRequirement.getApplyDeadline();
            if (deadline != null) {
                zbRequirement.setRemainTime(DateUtil.timeCountDown(deadline));
                //计算报名时间是否已截止
                Long deadLineTimeLong = deadline.getTime();
                Long currTimeLong = System.currentTimeMillis();
                if(deadLineTimeLong < currTimeLong){
                    zbRequirementVo.setIsApplyDeadline(0);
                }
            }

            Short typeId = zbRequirement.getType();
            ZbType zbType = zbTypeMapper.selectByPrimaryKey(typeId.intValue());
            zbRequirement.setTypeName(zbType == null? "未知类型" : zbType.getTypeName());
            //查看当前用户是否已报名
            BeanUtils.copyProperties(zbRequirement,zbRequirementVo);
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("requirementId",zbRequirement.getId()));
            filters.add(Condition.eq("userId",userId));
            List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyWebsiteService.selectList(filters);
            if(Objects.nonNull(zbRequirementApplies) && zbRequirementApplies.size() > 0){
                zbRequirementVo.setOperStatus(APPLY_STATUS_YES);
            } else {
                zbRequirementVo.setOperStatus(APPLY_STATUS_NO);
            }
            zbRequirementSPVos.add(zbRequirementVo);
        }
        return zbRequirementSPVos;
    }

    //服务商 - 我的任务 - 封装数据
    private List<ZbRequirementSPVo> copyMyTaskZbRequirementData(List<ZbRequirement> zbRequirements, List<ZbRequirementApply> zbRequirementApplies) throws Exception{
        List<ZbRequirementSPVo> zbRequirementSPVos = new ArrayList<>();
        if(null != zbRequirements && zbRequirements.size() > 0){
            for(ZbRequirement zbRequirement : zbRequirements){
                ZbRequirementSPVo zbRequirementVo = new ZbRequirementSPVo();
                BeanUtils.copyProperties(zbRequirement,zbRequirementVo);
                Long zId =  zbRequirement.getId();
                zbRequirementSPVos.add(zbRequirementVo);
                for(ZbRequirementApply zbRequirementApply : zbRequirementApplies){
                    Long requirementId = zbRequirementApply.getRequirementId();
                    if(requirementId.equals(zId)){
                        zbRequirementVo.setOperStatus(zbRequirementApply.getStatus().intValue());
                        break;
                    }
                }

            }
        }
        return zbRequirementSPVos;
    }

}
