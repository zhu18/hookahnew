package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementPageHelper;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ServiceProviderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.jusfoun.hookah.crowd.controller.BaseController.PAGE_NUM;

/**
 * 前台服务商专用
 * Created by ctp on 2017/10/10.
 */
@RestController
public class RequireServicerController {

    @Resource
    ServiceProviderService serviceProviderService;

    /**
     * 服务商需求大厅列表 ，根据条件展示需求列表
     * @author ctp
     */
    @RequestMapping(value = "/api/servicer/getRequirementList")
    public ReturnData getRequirementList(ZbRequirementPageHelper helper) {
        return serviceProviderService.getRequirementVoList(helper);
    }

    /**
     * 服务商需求大厅列表 - 我的任务 ，根据条件展示需求列表
     * @author ctp
     */
    @RequestMapping(value = "/api/myTask/getRequirementList")
    public ReturnData myTaskGetRequirementList(ZbRequirementPageHelper helper) {
        return serviceProviderService.getRequirementVoListByUserId(helper);
    }

    /**
     * 服务商需求大厅 - 我的任务 - 查看需求详情
     * @author ctp
     */
    @RequestMapping(value = "/api/myTask/getRequirementDetail")
    public ReturnData myTaskGetRequirementDetail(Long reqId) {
        return serviceProviderService.findByRequirementId(reqId);
    }

    /**
     * 平台前端 - 数据众包首页 - 引导页 - 查看需求详情
     * @author ctp
     */
    @RequestMapping(value = "/front/getRequirementDetail")
    public ReturnData websiteRequirementDetail(Long reqId) {
        return serviceProviderService.findByRequirementId(reqId);
    }

}
