package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.ZbComment;
import com.jusfoun.hookah.core.domain.zb.vo.ZbProgramVo;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbProgramService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by ctp on 2017/9/26.
 */
@Controller
@RequestMapping(value = "/api/program")
public class ZbProgramController extends BaseController {


    @Resource
    ZbProgramService zbProgramService;

    /**
     *前台选中方案提交
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public ReturnData add(@RequestBody ZbProgramVo zbProgramVo){
        return zbProgramService.insertRecord(zbProgramVo);
    }

    /**
     *前台选中方案重新提交
     * @return
     */
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public ReturnData edit(@RequestBody ZbProgramVo zbProgramVo){
        return zbProgramService.editProgram(zbProgramVo);
    }

    /**
     *前台选中方案重新提交
     * @return
     */
    @RequestMapping(value = "detail")
    @ResponseBody
    public ReturnData detail(Long reqId){
        return zbProgramService.selectProgramByReqId(reqId);
    }

    /**
     *前台选中方案重新提交
     * @return
     */
    @RequestMapping(value = "addComment")
    @ResponseBody
    public ReturnData detail(ZbComment zbComment){
        return zbProgramService.addRequirementComment(zbComment);
    }

}
