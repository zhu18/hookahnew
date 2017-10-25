package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbCommentMapper;
import com.jusfoun.hookah.core.dao.zb.ZbProgramMapper;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementApplyMapper;
import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.*;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbRequireStatus;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
    MgZbRequireStatusService mgZbRequireStatusService;

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
        if (requirementId == null) {
            return ReturnData.error();
        }
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("requirementId", requirementId));
        ZbRequirement zbRequirement = zbRequirementMapper.selectForDetail(requirementId);
        MgZbRequireStatus mgZbRequireStatus = mgZbRequireStatusService.getByRequirementSn(zbRequirement.getRequireSn());
        List<ZbRequirementApply> zbRequirementApplies = zbRequireApplyService.selectList(filters);
        ZbProgram zbProgram = null;
        List<ZbAnnex> reqProgram = new ArrayList<>();
        List<ZbComment> zbComments = zbCommentService.selectList(filters);
        List<Condition> filter = new ArrayList<>();
        for (ZbRequirementApply zbRequirementApply : zbRequirementApplies) {
            User user = userService.selectById(zbRequirementApply.getUserId());
            zbRequirementApply.setUserName(user.getUserName());
            zbRequirementApply.setMobile(user.getMobile());
            if (!Short.valueOf(zbRequirementApply.getStatus()).equals(ZbContants.ZbRequireMentApplyStatus.APPLY_SUCCESS.getCode().shortValue()) && !Short.valueOf(zbRequirementApply.getStatus()).equals(ZbContants.ZbRequireMentApplyStatus.LOSE_BID.getCode().shortValue())) {
                filters.clear();
                filters.add(Condition.eq("applyId", zbRequirementApply.getId()));
                zbProgram = zbProgramService.selectOne(filters);
                //1为方案附件
                if (zbProgram != null) {
                    filter.clear();
                    filter.add(Condition.eq("correlationId", zbProgram.getId()));
                    filter.add(Condition.eq("type", 1));
                    reqProgram = zbAnnexService.selectList(filter);
                }
            }
        }
        //0为需求附件
        filter.add(Condition.eq("correlationId", zbRequirement.getId()));
        filter.add(Condition.eq("type", 0));
        List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filter);



        Map<String, Object> map = new HashMap<>();
        map.put("zbRequirement", zbRequirement);
        map.put("zbRequirementApplies", zbRequirementApplies);
        map.put("zbProgram", zbProgram != null ? zbProgram : "");
        map.put("zbAnnexes", zbAnnexes != null ? zbAnnexes : "");
        map.put("zbComments", zbComments);
        map.put("mgZbRequireStatus", mgZbRequireStatus);
        map.put("reqProgram", reqProgram);
        return ReturnData.success(map);
    }

    @Override
    public ReturnData choseApply(Long id) {
        if (id == null) {
            return ReturnData.error("操作失败，请重新操作");
        }
        ZbRequirementApply zbRequirementApply = new ZbRequirementApply();
        zbRequirementApply.setId(id);
        zbRequirementApply.setStatus((short) 1);
        updateByIdSelective(zbRequirementApply);
        return ReturnData.success();
    }


}
