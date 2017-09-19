package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZbRequireServiceImpl extends GenericServiceImpl<ZbRequirement, Long> implements ZbRequireService {

    @Resource
    private ZbRequirementMapper zbRequirementMapper;

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
                                                   String title, String requireSn) throws HookahException{
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("userId",userId));
        if (StringUtils.isNotBlank(title)) filter.add(Condition.like("title",title));
        if (StringUtils.isNotBlank(requireSn)) filter.add(Condition.eq("requireSn",requireSn));
        if (status != null) filter.add(Condition.eq("status",status));

        List<OrderBy> orderBys = new ArrayList<>();
        orderBys.add(OrderBy.desc("addTime"));
        Pagination<ZbRequirement> list= getListInPage(pageNum, pageSize,filter,orderBys);
        return ReturnData.success(list);
    }

    @Override
    public ReturnData<ZbRequirement> getAllRequirement(String currentPage, String pageSize, ZbRequirement zbRequirement) {
        Pagination<ZbRequirement> page = new Pagination<>();
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));

            if (StringUtils.isNotBlank(zbRequirement.getRequireSn())) {
                filters.add(Condition.like("requireSn", zbRequirement.getRequireSn()));
            }
            if (StringUtils.isNotBlank(zbRequirement.getTitle())) {
                filters.add(Condition.like("title", zbRequirement.getTitle()));
            }
            if (zbRequirement.getStatus() != null && zbRequirement.getStatus()!= -1) {
                filters.add(Condition.eq("status", zbRequirement.getStatus()));
            }

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            returnData.setData(page);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


}
