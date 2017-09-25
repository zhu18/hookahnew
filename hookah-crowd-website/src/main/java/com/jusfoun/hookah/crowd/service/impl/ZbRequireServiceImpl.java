package com.jusfoun.hookah.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.dao.zb.ZbTypeMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.WithdrawVo;
import com.jusfoun.hookah.core.domain.zb.ZbAnnex;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementPageHelper;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.UserService;
import com.jusfoun.hookah.crowd.service.ZbAnnexService;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import com.jusfoun.hookah.crowd.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
            filter.add(Condition.eq("status", status));
        } else {
            filter.add(Condition.in("status", new Short[]{1, 2, 3, 7, 8, 10, 13, 16, 14, 15}));
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

            Short[] zbStatus = new Short[]{1, 4, 5, 9, 11, 14, 15, 16};
            if(zbRequirement.getStatus() != null && zbRequirement.getStatus() != -1){
                zbStatus = new Short[]{zbRequirement.getStatus()};
            }

            PageHelper.startPage(pageNumberNew, pageSizeNew);

            List<ZbRequirement> list = zbRequirementMapper.
                    selectListForPageByFilters(zbRequirement.getRequireSn(), zbRequirement.getTitle(), zbStatus);

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
    public ReturnData<ZbRequirement> updateStatus(String id, String status, String applyDeadline) {
        try {
            ZbRequirement zbRequirement = new ZbRequirement();
            zbRequirement.setId(Long.parseLong(id));
            zbRequirement.setStatus(Short.parseShort(status));
            zbRequirement.setPressTime(new Date());
            zbRequirement.setApplyDeadline(DateUtils.getDate(applyDeadline));
            super.updateByIdSelective(zbRequirement);
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
        if(zbr == null){
            return ReturnData.error("未获取相关信息！");
        }

        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("correlationId", zbr.getId()));
        filter.add(Condition.eq("type", 0));
        List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filter);
        Map<String, Object> map = new HashedMap();
        map.put("zbAnnexes", zbAnnexes);
        map.put("zbRequirement", zbr);
        returnData.setData(map);
        return returnData;
    }

    @Override
    public List<ZbRequirement> selectTradeListByUID(String userId) {
        return null;
    }

}
