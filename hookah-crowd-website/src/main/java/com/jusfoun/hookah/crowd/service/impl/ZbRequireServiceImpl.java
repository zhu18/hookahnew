package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
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
                                                   String requireSn, String title) throws HookahException{
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


}
