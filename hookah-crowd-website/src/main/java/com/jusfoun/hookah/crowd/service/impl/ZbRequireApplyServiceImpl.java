package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbProgramMapper;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementApplyMapper;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.zb.ZbProgram;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbProgramService;
import com.jusfoun.hookah.crowd.service.ZbRequireApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lt on 2017/9/19.
 */
@Service
public class ZbRequireApplyServiceImpl extends GenericServiceImpl<ZbRequirementApply, Long> implements ZbRequireApplyService {

    @Resource
    private ZbRequirementApplyMapper zbRequirementApplyMapper;

    @Resource
    ZbRequirementMapper zbRequirementMapper;

    @Resource
    ZbProgramService zbProgramService;

    @Resource
    public void setDao(ZbRequirementApplyMapper zbRequirementApplyMapper) {
        super.setDao(zbRequirementApplyMapper);
    }

    @Override
    public int insertRecord(ZbRequirementApply zbRequirementApply) {
        return zbRequirementApplyMapper.insertAndGetId(zbRequirementApply);
    }

    @Override
    public ReturnData viewApplyByRequire(Long requirementId) throws Exception {
        if (requirementId==null){
            return ReturnData.error();
        }
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("requirementId",requirementId));
        ZbRequirement zbRequirement = zbRequirementMapper.selectByPrimaryKey(requirementId);
        List<ZbRequirementApply> zbRequirementApplies = selectList(filters);
        List<ZbProgram> zbPrograms = new ArrayList<>();
        for (ZbRequirementApply zbRequirementApply:zbRequirementApplies){
            if (zbRequirementApply.getStatus().equals(1)){
                filters.clear();
                filters.add(Condition.eq("applyId",zbRequirementApply.getId()));
                zbPrograms = zbProgramService.selectList(filters);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("zbRequirement",zbRequirement);
        map.put("zbRequirementApplies",zbRequirementApplies);
        map.put("zbPrograms",zbPrograms);
        return ReturnData.success(map);
    }

    @Override
    public ReturnData choseApply(Long id) {
        if (id==null){
            return ReturnData.error("操作失败，请重新操作");
        }
        ZbRequirementApply zbRequirementApply = new ZbRequirementApply();
        zbRequirementApply.setId(id);
        zbRequirementApply.setStatus((short)1);
        updateByIdSelective(zbRequirementApply);
        return ReturnData.success();
    }


}
