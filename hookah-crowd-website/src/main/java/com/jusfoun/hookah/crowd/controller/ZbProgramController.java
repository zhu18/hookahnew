package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.ZbProgram;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbProgramService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by ctp on 2017/9/26.
 */
@Controller
public class ZbProgramController extends BaseController {


    @Resource
    ZbProgramService zbProgramService;

    /**
     *前台选中方案提交
     * @return
     */
//    @RequestMapping(value = "/api/apply/add",method = RequestMethod.GET)
//    @ResponseBody
//    public ReturnData add(ZbRequirementApply zbRequirementApply){
//        return null;
//    }

}
