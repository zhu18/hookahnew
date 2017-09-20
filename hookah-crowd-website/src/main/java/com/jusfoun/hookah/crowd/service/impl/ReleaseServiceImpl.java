package com.jusfoun.hookah.crowd.service.impl;

/**
 * Created by zhaoshuai on 2017/9/18.
 */

import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementFiles;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRequirementVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
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
    ZbRequirementFilesService zbRequirementFilesService;

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
                if(vo.getZbRequirement().getType() != null)
                    ment.setRequireSn(CommonUtils.getRequireSn("ZB",vo.getZbRequirement().getType().toString()));
                zbRequirementMapper.insertAndGetId(ment);

                if(vo.getFiles().size() > 0){
                    for(ZbRequirementFiles zbfile : vo.getFiles()){
                        zbfile.setRequirementId(ment.getId());
                        zbfile.setAddTime(new Date());
                        zbRequirementFilesService.insert(zbfile);
                    }
                }
            }else{
                ZbRequirement zbRequirement = zbRequireService.selectById(ment.getId());
                ment.setRequireSn(zbRequirement.getRequireSn());
                ment.setAddTime(zbRequirement.getAddTime());
                ment.setAddOperator(zbRequirement.getAddOperator());
                ment.setUpdateTime(new Date());
                ment.setUpdateOperator(vo.getZbRequirement().getUserId());
                super.updateById(ment);

                List<Condition> filter = new ArrayList<>();
                filter.add(Condition.eq("requirementId", ment.getId()));
                zbRequirementFilesService.deleteByCondtion(filter);

                if(vo.getFiles().size() > 0){
                    for(ZbRequirementFiles zbfile : vo.getFiles()){
                        zbfile.setRequirementId(ment.getId());
                        zbRequirementFilesService.insert(zbfile);
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
