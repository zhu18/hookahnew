package com.jusfoun.hookah.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.zb.ZbProgramMapper;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementApplyMapper;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.dao.zb.ZbTypeMapper;
import com.jusfoun.hookah.core.domain.zb.*;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbRequireStatus;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
import com.jusfoun.hookah.crowd.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ZbRequireServiceImpl extends GenericServiceImpl<ZbRequirement, Long> implements ZbRequireService {

    @Resource
    private ZbRequirementMapper zbRequirementMapper;

    @Resource
    ZbTypeMapper zbTypeMapper;

    @Resource
    ZbAnnexService zbAnnexService;

    @Resource
    UserService userService;

    @Resource
    ZbRequirementApplyMapper zbRequirementApplyMapper;

    @Resource
    ZbRequireApplyService zbRequireApplyService;

    @Resource
    ZbProgramMapper zbProgramMapper;

    @Resource
    ZbProgramService zbProgramService;

    @Resource
    MgZbRequireStatusService mgZbRequireStatusService;

    @Resource
    MgZbProviderService mgZbProviderService;


    @Resource
    public void setDao(ZbRequirementMapper zbRequirementMapper) {
        super.setDao(zbRequirementMapper);
    }


    @Override
    public int insertRecord(ZbRequirement zbRequirement) {
        return zbRequirementMapper.insertAndGetId(zbRequirement);
    }

    @Override
    public ReturnData<ZbRequirement> getListByUser(Integer pageNum, Integer pageSize, String userId, Integer status,
                                                   String title, String requireSn) throws HookahException {
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("userId", userId));
        if (StringUtils.isNotBlank(title)) filter.add(Condition.like("title", title));
        if (StringUtils.isNotBlank(requireSn)) filter.add(Condition.eq("requireSn", requireSn));
        if (status != null) {
            if(status == 5){
                filter.add(Condition.in("status", new Short[]{5, 6}));
            }else{
                filter.add(Condition.eq("status", status));
            }
        } else {
            filter.add(Condition.in("status", new Short[]{1, 2, 3, 5, 6, 7, 8, 10, 13, 16, 14, 15, 19}));
        }

        List<OrderBy> orderBys = new ArrayList<>();
        orderBys.add(OrderBy.desc("addTime"));
        try {
            Pagination<ZbRequirement> list = getListInPage(pageNum, pageSize, filter, orderBys);
            return ReturnData.success(list);
        } catch (Exception e) {
            logger.error("获取" + userId + "发布需求失败", e.getMessage());
            return ReturnData.error("系统错误：" + e.getMessage());
        }
    }

    @Override
    public ReturnData<ZbRequirement> getAllRequirement(String currentPage, String pageSize, ZbRequirement zbRequirement) {

        Pagination<ZbRequirement> pagination = new Pagination<>();
        PageInfo<ZbRequirement> pageInfo = new PageInfo<>();
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (com.jusfoun.hookah.core.utils.StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (com.jusfoun.hookah.core.utils.StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }

            Short[] zbStatus = new Short[]{1, 4, 5, 6, 9, 11, 14, 15, 16};
            if (zbRequirement.getStatus() != null && zbRequirement.getStatus() != -1) {
                zbStatus = new Short[]{zbRequirement.getStatus()};
            }

            PageHelper.startPage(pageNumberNew, pageSizeNew);

            List<ZbRequirement> list = zbRequirementMapper.
                    selectListForPageByFilters(zbRequirement.getRequireSn(), zbRequirement.getTitle(), zbStatus);

            for (ZbRequirement zbRequirement1 : list) {
                if (zbRequirement1.getStatus() == 5 && zbRequirement1.getApplyDeadline() != null) {
                    if (zbRequirement1.getApplyDeadline().getTime() <= new Date().getTime()) {
                        zbRequirement1.setStatus(ZbContants.Zb_Require_Status.SELECTING.getCode().shortValue());
                        zbRequirementMapper.updateByPrimaryKeySelective(zbRequirement1);
                    }
                }
            }
            pageInfo = new PageInfo<ZbRequirement>(list);

            pagination.setTotalItems(pageInfo.getTotal());
            pagination.setPageSize(pageSizeNew);
            pagination.setCurrentPage(pageNumberNew);
            pagination.setList(pageInfo.getList());
            returnData.setData(pagination);

//            List<Condition> filters = new ArrayList();
//            List<OrderBy> orderBys = new ArrayList();
//            orderBys.add(OrderBy.desc("addTime"));
//
//            if (StringUtils.isNotBlank(zbRequirement.getRequireSn())) {
//                filters.add(Condition.like("requireSn", zbRequirement.getRequireSn()));
//            }
//            if (StringUtils.isNotBlank(zbRequirement.getTitle())) {
//                filters.add(Condition.like(" title", zbRequirement.getTitle()));
//            }
//            if (zbRequirement.getStatus() != null && zbRequirement.getStatus() != -1) {
//                filters.add(Condition.eq("status", zbRequirement.getStatus()));
//            } else {
//                filters.add(Condition.in("status", new Short[]{1, 4, 5, 9, 11, 14, 15, 16}));
//            }
//            int pageNumberNew = HookahConstants.PAGE_NUM;
//            if (StringUtils.isNotBlank(currentPage)) {
//                pageNumberNew = Integer.parseInt(currentPage);
//            }
//
//            int pageSizeNew = HookahConstants.PAGE_SIZE;
//            if (StringUtils.isNotBlank(pageSize)) {
//                pageSizeNew = Integer.parseInt(pageSize);
//            }
//            page = getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
//            List<ZbRequirement> list = page.getList();
//            for (ZbRequirement zbRequirement1 : list) {
//                User user1 = userService.selectById(zbRequirement1.getUserId());
//                if (user1.getUserType() == 4) {
//                    zbRequirement1.setRequiremetName(user1.getOrgName());
//                } else {
//                    zbRequirement1.setRequiremetName(user1.getUserName());
//                }
//            }
//            returnData.setData(page);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @Override
    public ReturnData<ZbRequirement> updateStatus(String id, String status, String applyDeadline, Long applyId, Long programId, String checkAdvice) {
        try {
            ZbRequirement zbRequirement = this.selectById(Long.parseLong(id));
            if (status != null) {
                zbRequirement.setStatus(Short.parseShort(status));
            }
            zbRequirement.setPressTime(new Date());
            if (applyDeadline != null) {
                zbRequirement.setApplyDeadline(DateUtils.getDate(applyDeadline));
            }
            ZbRequirementApply zbRequirementApply = zbRequirementApplyMapper.selectByPrimaryKey(applyId);
            if (Short.valueOf(status).equals(ZbContants.Zb_Require_Status.WAIT_TWO_TG.getCode().shortValue()) && zbRequirementApply.getStatus() != null) {
                zbRequirementApply.setId(applyId);
                zbRequirementApply.setStatus(ZbContants.ZbRequireMentApplyStatus.WORKING.getCode().shortValue());
                zbRequireApplyService.updateByIdSelective(zbRequirementApply);

                //其他未选中用户改为未中标
                List<Condition> filters = new ArrayList<>();
                filters.add(Condition.eq("requirementId", id));
                filters.add(Condition.ne("userId", zbRequirementApply.getUserId()));
                ZbRequirementApply cancelApply = new ZbRequirementApply();
                cancelApply.setStatus(ZbContants.ZbRequireMentApplyStatus.LOSE_BID.getCode().shortValue());
                zbRequireApplyService.updateByConditionSelective(cancelApply, filters);
            }
            ZbProgram zbProgram = zbProgramMapper.selectByPrimaryKey(programId);
            if (Short.valueOf(status).equals(ZbContants.Zb_Require_Status.WAIT_buyer_YS.getCode().shortValue()) && zbProgram.getStatus() != null) {
                zbProgram.setId(programId);
                zbProgram.setStatus(ZbContants.Zb_Require_Status.WAIT_CHECK.getCode().shortValue());
                //添加平台预评时间
                mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.PLATEVALTIME, DateUtil.getSimpleDate(zbProgram.getAddTime()));

                if (checkAdvice != null) {
                    zbProgram.setCheckAdvice(checkAdvice);
                }
                zbProgramService.updateByIdSelective(zbProgram);
                //修改报名表状态为验收中（ACCEPTANCE）
                ZbRequirementApply apply = new ZbRequirementApply();
                apply.setId(zbProgram.getApplyId());
                apply.setStatus(ZbContants.ZbRequireMentApplyStatus.ACCEPTANCE.getCode().shortValue());
                zbRequireApplyService.updateByIdSelective(apply);
            }
            if (Short.valueOf(status).equals(ZbContants.Zb_Require_Status.WAIT_PLAT_YS.getCode().shortValue()) && zbProgram.getStatus() != null) {
                zbProgram.setId(programId);
                zbProgram.setStatus(ZbContants.Zb_Require_Status.CHECK_NOT.getCode().shortValue());
                if (checkAdvice != null) {
                    zbProgram.setCheckAdvice(checkAdvice);
                }
                zbProgramService.updateByIdSelective(zbProgram);
                //平台审核不通过 修改报名表状态为工作中
//                ZbRequirementApply apply = new ZbRequirementApply();
//                apply.setId(zbProgram.getApplyId());
//                apply.setStatus(ZbContants.ZbRequireMentApplyStatus.WORKING.getCode().shortValue());
//                zbRequireApplyService.updateByIdSelective(apply);
            }
            zbRequirement.setUpdateTime(new Date());
            super.updateByIdSelective(zbRequirement);
            if (Short.valueOf(status).equals(ZbContants.Zb_Require_Status.WAIT_TWO_TG.getCode().shortValue())) {
                //添加资格评选时间
                mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.SELECTTIME, DateUtil.getSimpleDate(zbRequirement.getUpdateTime()));
            }
            if (Short.valueOf(status).equals(ZbContants.Zb_Require_Status.SINGING.getCode().shortValue())) {
                //添加平台发布时间
                mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.PRESSTIME, DateUtil.getSimpleDate(zbRequirement.getPressTime()));
            }

        } catch (Exception e) {
            return ReturnData.error("发布失败");
        }
        return ReturnData.success("发布成功");
    }

    @Override
    public ReturnData<ZbRequirement> getRequirementList(ZbRequirementPageHelper helper) {
        ReturnData returnData = new ReturnData();
        try {
            Pagination<ZbRequirement> pagination = new Pagination<>();
            int startIndex = (helper.getPageNumber() - 1) * helper.getPageSize();
            helper.setStartIndex(startIndex);
            int count = zbRequirementMapper.countRequirementList(helper);
            List<ZbRequirement> list = zbRequirementMapper.getRequirementList(helper);
            for (ZbRequirement requirement : list) {
                Date deadline = requirement.getApplyDeadline();
                if (deadline != null) requirement.setRemainTime(DateUtil.timeCountDown(deadline));
                List<Condition> filters2 = new ArrayList<>();
                if (requirement.getId() != null) {
                    filters2.add(Condition.eq("requirementId", requirement.getId()));
                }
                List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyService.selectList(filters2);
                requirement.setCount(zbRequirementApplies.size());
            }
            pagination.setTotalItems(count);
            pagination.setPageSize(helper.getPageSize());
            pagination.setCurrentPage(helper.getPageNumber());
            pagination.setList(list);
            returnData.setData(pagination);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ZbRequireServiceImpl-->getRequirementList", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统错误：" + e.getMessage());
        } finally {
            return returnData;
        }
    }

    @Override
    public ReturnData<ZbRequirement> reqCheck(String id) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        ZbRequirement zbr = zbRequirementMapper.selectForDetail(Long.parseLong(id));
        if (zbr == null) {
            return ReturnData.error("未获取相关信息！");
        }

        //添加状态时间
        MgZbRequireStatus mgZbRequireStatus = mgZbRequireStatusService.getByRequirementSn(zbr.getRequireSn());

        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("correlationId", zbr.getId()));
        filter.add(Condition.eq("type", 0));
        List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filter);
        Map<String, Object> map = new HashedMap();
        map.put("zbAnnexes", zbAnnexes);
        map.put("zbRequirement", zbr);
        map.put("mgZbRequireStatus", mgZbRequireStatus != null ? mgZbRequireStatus : new MgZbRequireStatus());
        returnData.setData(map);
        return returnData;
    }

    @Override
    public List<ZbRequirement> selectTradeListByUID(String userId) {
        return null;
    }

    @Override
    public ReturnData selectRequirementTypeInfo() {
        Map<String, Object> map = new HashMap<>(6);
        //数据采集
        map.put("dataCollection", applyLastTime(Short.valueOf("1")));
        //数据清洗
        map.put("dataProcess", applyLastTime(Short.valueOf("2")));
        //数据模型
        map.put("dataModel", applyLastTime(Short.valueOf("4")));
        //数据应用
        map.put("datApplication", applyLastTime(Short.valueOf("5")));
        //数据分析
        map.put("dataCleansing", applyLastTime(Short.valueOf("3")));
        //其他
        map.put("otherData", applyLastTime(Short.valueOf("6")));
        return ReturnData.success(map);
    }


    //计算截止报名时间的剩余时间
    public Object applyLastTime(Short type) {
        List<ZbRequirement> zbRequirement = null;
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("type", type));
            filters.add(Condition.in("status", new Short[]{5}));
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.asc("applyDeadline"));
            //从session获取用户信息
            Map userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
            if (userMap != null) {
                //登录
                List<Condition> filters1 = new ArrayList<>();
                filters1.add(Condition.eq("userId", this.getCurrentUser().getUserId()));
                List<ZbRequirementApply> applies = zbRequireApplyService.selectList(filters1);
                List<Long> list = new ArrayList();
                for (ZbRequirementApply app : applies) {
                    list.add(app.getRequirementId());
                }
                if (list != null && list.size() > 0) {
                    filters.add(Condition.notIn("id", list.toArray()));
                }
                zbRequirement = this.selectList(filters, orderBys);
                //zbRequirement = this.selectList(filters);
            } else {
                //未登录
                zbRequirement = this.selectList(filters, orderBys);
                //zbRequirement = this.selectList(filters);
            }
            if (zbRequirement != null) {
                for (ZbRequirement zb : zbRequirement) {
                    if (zb != null) {
                        if (zb.getApplyDeadline() != null) {
                            String time = DateUtil.timeCountDown(zb.getApplyDeadline());
                            zb.setApplyLastTime(time != null ? time : "");
                        }
                        List<Condition> filters2 = new ArrayList<>();
                        filters2.add(Condition.eq("requirementId", zb.getId()));
                        List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyService.selectList(filters2);
                        zb.setCount(zbRequirementApplies.size());
                    }
                }
            }
            return zbRequirement != null ? zbRequirement : "";
        } catch (Exception e) {
            logger.error("系统错误", e);
            return "系统错误";
        }
    }

    //服务商管理
    @Override
    public ReturnData<MgZbProvider> getAllProvider(String currentPage, String pageSize, Integer authType, Integer status, String upname, String userId, Date startTime, Date endTime) {

        PageInfo<MgZbProvider> pageInfo = new PageInfo<>();
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (com.jusfoun.hookah.core.utils.StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (com.jusfoun.hookah.core.utils.StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            PageHelper.startPage(pageNumberNew, pageSizeNew);
            List<Condition> filters = new ArrayList();
            if (authType != null) {
                filters.add(Condition.eq("authType", authType));
            }

            if (status != null && status != -1) {
                filters.add(Condition.eq("status", status));
            }
            if (upname != null) {
                filters.add(Condition.like("upname", upname));
            }
            if (userId != null) {
                filters.add(Condition.eq("userId", userId));
            }

            List<Sort> sorts = new ArrayList<>();
            sorts.add(new Sort(Sort.Direction.DESC, "addTime"));

            Pagination<MgZbProvider> pagination = mgZbProviderService.getListInPageFromMongo(pageNumberNew, pageSizeNew, filters, sorts, startTime, endTime);

            returnData.setData(pagination);

        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    public ReturnData checkEnroll(Long id, Long requirementId) {
        if (id != null && requirementId != null) {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("requirementId", requirementId));
            List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyService.selectList(filters);
            for (ZbRequirementApply zb : zbRequirementApplies) {
                //zb.setId(zb.getId());
                if (zb.getId().equals(id)) {
                    zb.setStatus(ZbContants.ZbRequireMentApplyStatus.WORKING.code.shortValue());
                } else {
                    zb.setStatus(ZbContants.ZbRequireMentApplyStatus.LOSE_BID.code.shortValue());
                }
                zbRequireApplyService.updateById(zb);
            }
            ZbRequirement zbRequirement = this.selectById(requirementId);
            int i = 0;
            if (zbRequirement != null) {
                if (zbRequirement.getTrusteePercent() == 100) {
                    zbRequirement.setStatus(ZbContants.Zb_Require_Status.WORKINGING.code.shortValue());
                } else {
                    zbRequirement.setStatus(ZbContants.Zb_Require_Status.WAIT_TWO_TG.code.shortValue());
                }
                zbRequirement.setUpdateTime(new Date());
                i = this.updateById(zbRequirement);
                if (i > 0) {
                    //添加资格评选时间
                    mgZbRequireStatusService.setRequireStatusInfo(zbRequirement.getRequireSn(), ZbContants.SELECTTIME, DateUtil.getSimpleDate(zbRequirement.getUpdateTime()));
                    return ReturnData.success(i);
                }
            }
        }
        return ReturnData.error("任务报名选中失败！");
    }

    //服务商审核
    @Override
    public ReturnData<MgZbProvider> provideCheck(String userId, Integer status) {
        try {
            MgZbProvider mgZbProvider = new MgZbProvider();
            mgZbProvider.setUserId(userId);
            mgZbProvider.setStatus(status);
            mgZbProviderService.updateByIdSelective(mgZbProvider);
            return ReturnData.success("审核成功");
        } catch (Exception e) {
            return ReturnData.error("审核失败");
        }
    }
}
