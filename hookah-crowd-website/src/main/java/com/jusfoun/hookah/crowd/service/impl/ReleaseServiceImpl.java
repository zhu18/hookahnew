package com.jusfoun.hookah.crowd.service.impl;

/**
 * Created by zhaoshuai on 2017/9/18.
 */

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementFiles;
import com.jusfoun.hookah.core.domain.zb.ZbTag;
import com.jusfoun.hookah.core.domain.zb.ZbType;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRequirementVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.*;
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
    ZbTagService zbTagService;

    @Resource
    ZbRequirementFilesService zbRequirementFilesService;

    @Resource
    ReleaseService releaseService;

    @Resource
    ZbRequireService zbRequireService;

    @Resource
    ZbTypeService zbTypeService;

    /**
     * 数据众包--发布需求
     */
    public ReturnData insertRequirements(ZbRequirementVo vo){
        if(vo.getZbRequirement() != null){
            ZbRequirement ment = vo.getZbRequirement();
//            ment.setAddTime(new Date());
//            ment.setUserId(userId);
//            ment.setContactName(zbRequirement.getContactName());
//            ment.setContactPhone(zbRequirement.getContactPhone());
//            ment.setStatus(Short.parseShort("0"));
//            ment.setAddOperator(userId);
//            ment.setDescription(zbRequirement.getDescription());
//            ment.setDeliveryDeadline(zbRequirement.getDeliveryDeadline());
//            ment.setRewardMoney(zbRequirement.getRewardMoney());
//            ment.setCheckRemark(zbRequirement.getCheckRemark());
//            ment.setType(zbRequirement.getType());
            super.insert(ment);

            if(vo.getList().size() > 0){
                for(ZbRequirementFiles file : vo.getList()){
                    zbRequirementFilesService.insert(file);
                }
            }

            /*if(vo.getZbRequirement().getId()){

            }*/
            return ReturnData.success();
        }else {
            return  ReturnData.error("发布需求失败");
        }
    }

    /**
     * 数据众包--添加标签
     * @param tagContent 标签内容
     * @param type 标签类型
     */
    public void insertTag(String tagContent, Short type, String userId){
        ZbTag tag = new ZbTag();
        tag.setAddTime(new Date());
        tag.setTagContent(tagContent);
        tag.setType(type);
        tag.setAddOperator(userId);
        zbTagService.insert(tag);
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
        ZbRequirement zbRequirement = zbRequireService.selectOne(filters);
        if(zbRequirement.getId() != null){
            filters1.add(Condition.eq("requirementId", zbRequirement.getId()));
        }
        List<ZbRequirementFiles> zbRequirementFiles = zbRequirementFilesService.selectList(filters1);
        map.put("zbRequirement",zbRequirement);
        map.put("zbRequirementFiles",zbRequirementFiles);
        return ReturnData.success(map);
    }
}
