package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbRequireApplyService;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by lt on 2017/9/19.
 */
@Controller
public class RequireApplyController extends BaseController{

    @Resource
    ZbRequireService zbRequireService;
    @Resource
    ZbRequireApplyService zbRequireApplyService;

    @RequestMapping(value = "/requireApply/{id}",method = RequestMethod.GET)
    public ReturnData viewApplyByRequire(@PathVariable Integer id){
        try {
            zbRequireApplyService.viewApplyByRequire(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
