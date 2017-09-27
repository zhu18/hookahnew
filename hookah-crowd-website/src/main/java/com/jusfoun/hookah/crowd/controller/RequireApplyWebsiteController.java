package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbRequireApplyWebsiteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by ctp on 2017/9/26.
 */
@Controller
public class RequireApplyWebsiteController extends BaseController {

    @Resource
    ZbRequireApplyWebsiteService zbRequireApplyWebsiteService;

    /**
     *前台需求大厅  需求报名
     * @return
     */
    @RequestMapping(value = "/api/apply/add",method = RequestMethod.POST)
    @ResponseBody
    public ReturnData add(ZbRequirementApply zbRequirementApply){
        return zbRequireApplyWebsiteService.addApplay(zbRequirementApply);
    }


}
