package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementFiles;
import com.jusfoun.hookah.core.domain.zb.vo.ZbRequirementVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ReleaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zhaoshuai on 2017/9/18.
 */

@Controller
@RequestMapping("/release")
public class ReleaseController extends BaseController{

    @Resource
    ReleaseService releaseService;

    /**
     * 数据众包-发布需求
     */
    @ResponseBody
    @RequestMapping(value = "/insertRequirements", method = RequestMethod.POST)
    public ReturnData ReleaseRequirements(@RequestBody ZbRequirementVo vo){
        try {
            /*String userId = this.getCurrentUser().getUserId();
            vo.getZbRequirement().setUserId(userId);*/
            ReturnData returnData = releaseService.insertRequirements(vo);
            return ReturnData.success(returnData);
        } catch (Exception e) {
            logger.error("发布需求失败",e);
            return ReturnData.error("发布需求失败");
        }
    }

    /**
     * 数据众包-发布需求--确认信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/requirementInfo", method = RequestMethod.GET)
    public ReturnData requirementInfo(){
        try {
            String userId = this.getCurrentUser().getUserId();
            ReturnData requirementInfo = releaseService.getRequirementInfo(userId);
            return ReturnData.success(requirementInfo);
        } catch (HookahException e) {
            logger.error("查询发布需求失败",e);
            return ReturnData.error("查询发布需求失败");
        }
    }
}
