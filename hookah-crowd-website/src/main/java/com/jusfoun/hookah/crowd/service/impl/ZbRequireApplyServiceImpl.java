package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbCommentMapper;
import com.jusfoun.hookah.core.dao.zb.ZbProgramMapper;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementApplyMapper;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.zb.*;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
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
    ZbAnnexService zbAnnexService;

    @Resource
    UserService userService;

    @Resource
    ZbRequireApplyService zbRequireApplyService;

    @Resource
    ZbCommentService zbCommentService;
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
        ZbRequirement zbRequirement = zbRequirementMapper.selectForDetail(requirementId);
        List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyService.selectList(filters);
        List<ZbComment> zbComments = zbCommentService.selectList(filters);
        List<ZbProgram> zbPrograms = new ArrayList<>();
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("correlationId", zbRequirement.getId()));
        List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filter);
        for (ZbRequirementApply zbRequirementApply:zbRequirementApplies){
           zbRequirementApply.setUserName(userService.selectById(zbRequirement.getUserId()).getUserName());
           zbRequirementApply.setMobile(userService.selectById(zbRequirement.getUserId()).getMobile());
            if (Short.valueOf(zbRequirementApply.getStatus()).equals(ZbContants.Zb_Require_Status.WAIT_CHECK.getCode().shortValue())){
                filters.clear();
                filters.add(Condition.eq("applyId",zbRequirementApply.getId()));
                zbPrograms = zbProgramService.selectList(filters);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("zbRequirement",zbRequirement);
        map.put("zbRequirementApplies",zbRequirementApplies);
        map.put("zbPrograms",zbPrograms);
        map.put("zbAnnexes", zbAnnexes);
        map.put("zbComments", zbComments);
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