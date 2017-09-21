package com.jusfoun.hookah.crowd.service.impl;

/**
 * Created by zhaoshuai on 2017/9/18.
 */

import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.zb.ZbAnnex;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRequirementVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
import com.jusfoun.hookah.crowd.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zhaoshuai on 2017/7/12.
 */
@Service
public class ReleaseServiceImpl extends GenericServiceImpl<ZbRequirement, String> implements ReleaseService {

    @Resource
    ZbAnnexService zbAnnexService;

    @Resource
    ZbRequireService zbRequireService;

    @Resource
    ZbTypeService zbTypeService;

    @Resource
    private ZbRequirementMapper zbRequirementMapper;

    @Resource
    public void setDao(ZbRequirementMapper zbRequirementMapper) {
        super.setDao(zbRequirementMapper);
    }

    /**
     * 数据众包--发布需求
     */
    public ReturnData insertRequirements(ZbRequirementVo vo){

        if(vo.getZbRequirement() != null){
            ZbRequirement ment = vo.getZbRequirement();
            ment.setStatus(Short.parseShort("0"));
            if(ment.getId() == null){
                ment.setAddOperator(vo.getZbRequirement().getUserId());
                ment.setAddTime(new Date());
                ment.setRewardMoney(vo.getZbRequirement().getRewardMoney()*100);
                if(vo.getZbRequirement().getType() != null)
                    ment.setRequireSn(CommonUtils.getRequireSn("ZB",vo.getZbRequirement().getType().toString()));
                zbRequirementMapper.insertAndGetId(ment);

                if(vo.getAnnex().size() > 0){
                    for(ZbAnnex zbAnnex : vo.getAnnex()){
                        zbAnnex.setCorrelationId(ment.getId());
                        zbAnnex.setAddTime(new Date());
                        zbAnnex.setType(Short.parseShort("0"));
                        zbAnnexService.insert(zbAnnex);
                    }
                }
            }else{
                ZbRequirement zbRequirement = zbRequireService.selectById(ment.getId());
                if(zbRequirement != null){
                    ment.setRequireSn(zbRequirement.getRequireSn());
                    ment.setAddTime(zbRequirement.getAddTime());
                    ment.setAddOperator(zbRequirement.getAddOperator());
                    ment.setUpdateTime(new Date());
                    ment.setUpdateOperator(vo.getZbRequirement().getUserId());
                    ment.setRewardMoney(vo.getZbRequirement().getRewardMoney()*100);
                    super.updateById(ment);

                    List<Condition> filter = new ArrayList<>();
                    filter.add(Condition.eq("correlationId", ment.getId()));
                    zbAnnexService.deleteByCondtion(filter);
                    if(vo.getAnnex() != null){
                        if(vo.getAnnex().size() > 0){
                            for(ZbAnnex zbAnnex : vo.getAnnex()){
                                zbAnnex.setCorrelationId(ment.getId());
                                zbAnnex.setAddTime(new Date());
                                zbAnnex.setType(Short.parseShort("0"));
                                zbAnnexService.insert(zbAnnex);
                            }
                        }
                    }
                }
            }
            return ReturnData.success(vo);
        }else {
            return  ReturnData.error("发布需求失败");
        }
    }


    /**
     * 数据众包-发布需求--确认信息
     * @return
     */
    public ReturnData getRequirementInfo(String userId){
        Map<String, Object> map = new HashMap<>(6);
        List<Condition> filters = new ArrayList<>();
        List<Condition> filters1 = new ArrayList<>();
        if(StringUtils.isNotBlank(userId)){
            filters.add(Condition.eq("userId", userId));
        }
        filters.add(Condition.eq("status", 0));
        ZbRequirement zbRequirement = zbRequireService.selectOne(filters);
        if(zbRequirement != null){
            if(zbRequirement.getTag() != null){
                String[] strArray = null;
                strArray = zbRequirement.getTag().split(",");
                map.put("tag",strArray);
            }

            if(zbRequirement.getId() != null){
                filters1.add(Condition.eq("correlationId", zbRequirement.getId()));
            }
            List<ZbAnnex> zbAnnexes = zbAnnexService.selectList(filters1);
            map.put("zbRequirement",zbRequirement);
            map.put("zbRequirementFiles",zbAnnexes);
        }
        return ReturnData.success(map);
    }

    /**
     * 数据众包-发布需求--变更待审核状态需求
     * @return
     */
    public ReturnData getRequirementSubmit(Long id){
        ZbRequirement zbRequirement = zbRequireService.selectById(id);
        zbRequirement.setStatus(ZbContants.Zb_Require_Status.WAIT_CHECK.getCode().shortValue());
        List<Condition> filters = new ArrayList<>();
        if(StringUtils.isNotBlank(id.toString())){
            filters.add(Condition.eq("id", id));
        }
        int i = zbRequireService.updateByConditionSelective(zbRequirement, filters);
        return ReturnData.success(i);
    }
}
