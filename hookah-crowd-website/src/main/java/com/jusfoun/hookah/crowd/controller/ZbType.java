package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhaoshuai on 2017/9/19.
 */
@Controller
@RequestMapping("/zbType")
public class ZbType extends BaseController{

    @Resource
    ZbTypeService zbTypeService;

    /**
     * 数据众包-需求类型
     */
    @ResponseBody
    @RequestMapping(value = "/requirementsType", method = RequestMethod.GET)
    public ReturnData ReleaseRequirements(){
        List<com.jusfoun.hookah.core.domain.zb.ZbType> zbTypes = zbTypeService.selectList();
        return ReturnData.success(zbTypes);
    }
}
