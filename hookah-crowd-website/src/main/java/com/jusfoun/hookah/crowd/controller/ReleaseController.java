package com.jusfoun.hookah.crowd.controller;

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
@RequestMapping("/api/release")
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
            String userId = this.getCurrentUser().getUserId();
            vo.getZbRequirement().setUserId(userId);
            ReturnData returnData = releaseService.insertRequirements(vo);
            return returnData;
        } catch (Exception e) {
            logger.error("发布需求失败",e);
            return ReturnData.error("发布需求失败");
        }
    }

    /**
     * 数据众包-发布需求--查询草稿状态需求
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/requirementInfo", method = RequestMethod.GET)
    public ReturnData requirementInfo(){
        try {
            String userId = this.getCurrentUser().getUserId();
            ReturnData requirementInfo = releaseService.getRequirementInfo(userId);
            return requirementInfo;
        } catch (Exception e) {
            logger.error("查询发布需求失败",e);
            return ReturnData.error("查询发布需求失败");
        }
    }

    /**
     * 数据众包-发布需求--变更待审核状态需求
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/requirementSubmit", method = RequestMethod.GET)
    public ReturnData requirementSubmit(Long id){
        try {
            ReturnData requirementSubmit = releaseService.getRequirementSubmit(id);
            return requirementSubmit;
        } catch (Exception e) {
            logger.error("需求提交失败",e);
            return ReturnData.error("需求提交失败");
        }
    }

    /**
     * 数据众包-需求方-我的发布
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/releaseStatus", method = RequestMethod.GET)
    public ReturnData releaseStatus(Long id){
        try {
            //String userId = this.getCurrentUser().getUserId();
            ReturnData releaseStatus = releaseService.getUpdateReleaseStatus(id);
            return releaseStatus;
        } catch (Exception e) {
            logger.error("getUpdateReleaseStatus", e);
            return ReturnData.error("系统错误：" + e.getMessage());
        }
    }


    /**
     * 数据众包-需求方-添加验收意见
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertAcceptanceAdvice", method = RequestMethod.GET)
    public ReturnData acceptanceAdvice(Short status, String checkAdvice, Long id){
        ReturnData acceptanceAdvice = releaseService.getAcceptanceAdvice(status, checkAdvice, id);
        return acceptanceAdvice;
    }

    /**
     * 数据众包-需求方-添加对服务商的评价
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertEvaluation", method = RequestMethod.GET)
    public ReturnData insertEvaluation(int level, String content, Long programId){
        try {
            String userId = this.getCurrentUser().getUserId();
            ReturnData evaluation = releaseService.getInsertEvaluation(level, content, programId, userId);
            return evaluation;
        } catch (Exception e) {
            logger.error("getInsertEvaluation", e);
            return ReturnData.error("系统错误：" + e.getMessage());
        }
    }
}
