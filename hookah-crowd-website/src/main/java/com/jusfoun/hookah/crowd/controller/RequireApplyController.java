package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbRequireApplyService;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by lt on 2017/9/19.
 */
@Controller
public class RequireApplyController extends BaseController{

    @Resource
    ZbRequireApplyService zbRequireApplyService;

    /**
     *后台需求大厅  查看需求报名情况
     * @return
     */
    @RequestMapping(value = "/requireApply/viewApply",method = RequestMethod.GET)
    @ResponseBody
    public ReturnData viewApplyByRequire(Long id){
        try {
            return zbRequireApplyService.viewApplyByRequire(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.error("");
        }
    }

    /**
     * 后台  报名资格筛选
     * @return
     */
    @RequestMapping(value = "/requireApply/choseApply",method = RequestMethod.POST)
    @ResponseBody
    public ReturnData choseApply(Long id){
        try {
            return zbRequireApplyService.choseApply(id);
        } catch (Exception e){
            logger.error(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
    }

    /**
     * 后台  审核方案
     * @return
     */
    public ReturnData checkProgram(){
        return null;
    }
}
