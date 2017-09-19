package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementCheck;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbRequireCheckService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by crs on 2017/9/19.
 */
@Controller
public class RequireCheckController extends BaseController{
    @Resource
    ZbRequireCheckService zbRequireCheckService;

    /**
     * 需求大厅
     * @author crs
     */
    @RequestMapping("/require/requirementCheck")
    @ResponseBody
    public ReturnData requirementCheck(ZbRequirementCheck zbRequirementCheck) {
        try {
            return zbRequireCheckService.requirementCheck(zbRequirementCheck);
        }catch (Exception e){
            logger.error("需求查询失败", e);
            return ReturnData.error("需求查询失败");
        }
    }

}
