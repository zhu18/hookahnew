package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.User;
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
    @RequestMapping("/api/require/requirementCheck")
    @ResponseBody
    public ReturnData requirementCheck(ZbRequirementCheck zbRequirementCheck, User user) {
        try {
            user=getCurrentUser();
            return zbRequireCheckService.requirementCheck(zbRequirementCheck,user);
        }catch (Exception e){
            logger.error("审核失败", e);
            return ReturnData.error("审核失败");
        }
    }

}
