package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.dao.zb.ZbTypeMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementPageHelper;
import com.jusfoun.hookah.core.domain.zb.ZbType;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRequirementSPVo;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRequirementVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.service.ServiceProviderService;
import com.jusfoun.hookah.crowd.service.ZbRequireApplyWebsiteService;
import com.jusfoun.hookah.crowd.service.ZbTypeService;
import com.jusfoun.hookah.crowd.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
            //获取当前用户
            String userId = this.getCurrentUser() == null ? "" : this.getCurrentUser().getUserId();
            List<Condition> filters = new ArrayList<Condition>();
            if(Objects.nonNull(helper.getApplyStatus()) && Short.valueOf("-1").equals(helper.getApplyStatus())){
                filters.add(Condition.eq("status", helper.getApplyStatus()));
            }
            filters.add(Condition.eq("userId",userId));
            List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyWebsiteService.selectList(filters);
            List<Long> zbRequirementIds = new ArrayList<>();
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
        return null;
    }

    //服务商 - 需求大厅 - 封装数据
    private List<ZbRequirementSPVo> copyZbRequirementData(List<ZbRequirement> zbRequirements, String userId) throws Exception{
        List<ZbRequirementSPVo> zbRequirementSPVos = new ArrayList<>();
        for(ZbRequirement zbRequirement : zbRequirements){
            //计算报名截止剩余时间
            Date deadline = zbRequirement.getApplyDeadline();
            if (deadline != null) zbRequirement.setRemainTime(DateUtil.timeCountDown(deadline));
            Short typeId = zbRequirement.getType();
            ZbType zbType = zbTypeMapper.selectByPrimaryKey(typeId.intValue());
            zbRequirement.setTypeName(zbType == null? "未知类型" : zbType.getTypeName());
            //查看当前用户是否已报名
            ZbRequirementSPVo zbRequirementVo = new ZbRequirementSPVo();
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
