package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsFavorite;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.GoodsShelvesVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huang lei
 * @date 2017/3/6 上午10:52
 * @desc
 */
@Controller
public class CrowdSourcingController {
    /**
     * 数据众包首页
     */
    @RequestMapping(value = "/crowdsourcing", method = RequestMethod.GET)
    public String index(Model model) {
        return "crowdsourcing/index";
    }
    /**
     * 数据众包首页
     */
    @RequestMapping(value = "/crowdsourcing/demandGuide", method = RequestMethod.GET)
    public String demandGuide(Model model) {
        return "crowdsourcing/demandGuide";
    }
    /**
     * 官网展示 更多需求列表
     */
    @RequestMapping(value = "/crowdsourcing-list", method = RequestMethod.GET)
    public String crowdsourcingList(Model model) {
        return "crowdsourcing/crowdsourcing-list";
    }

    /**
     * 需求方发布需求页面
     */
    @RequestMapping(value = "/usercenter/crowdsourcingRelease", method = RequestMethod.GET)
    public String crowdsourcingRelease(Model model) {
        return "usercenter/crowdsourcing/demand/crowdsourcingRelease";
    }

    /**
     * 需求方 我发布的需求列表
     */
    @RequestMapping(value = "/usercenter/myRequirements", method = RequestMethod.GET)
    public String myRequirement(Model model) {
        return "usercenter/crowdsourcing/demand/myRequirements";
    }

    /**
     * 需求方 需求详情
     */
    @RequestMapping(value = "/usercenter/requirementDetail", method = RequestMethod.GET)
    public String requirementDetail(Model model) {
        return "usercenter/crowdsourcing/demand/requirementDetail";
    }

    /**
     * 服务商 需求大厅 所有需求列表
     */
    @RequestMapping(value = "/usercenter/demandList", method = RequestMethod.GET)
    public String demandList(Model model) {
        return "usercenter/crowdsourcing/service/demandList";
    }

    /**
     * 服务商 我的任务列表
     */
    @RequestMapping(value = "/usercenter/myMissionList", method = RequestMethod.GET)
    public String myMissionList(Model model) {
        return "usercenter/crowdsourcing/service/myMissionList";
    }

    /**
     * 服务商 需求详情
     */
    @RequestMapping(value = "/usercenter/missionDetail", method = RequestMethod.GET)
    public String missionDetail(Model model) {
        return "usercenter/crowdsourcing/service/missionDetail";
    }
    /**
     * 服务商 报名
     */
    @RequestMapping(value = "/usercenter/missionApply", method = RequestMethod.GET)
    public String missionApply(Model model) {
        return "usercenter/crowdsourcing/service/missionApply";
    }

    /**
     * 服务商 引导页
     */
    @RequestMapping(value = "/usercenter/authentication", method = RequestMethod.GET)
    public String authentication(Model model) {
        return "usercenter/crowdsourcing/auth/authentication";
    }/**
     * 服务商 个人认证
     */
    @RequestMapping(value = "/usercenter/pspAuthentication", method = RequestMethod.GET)
    public String pspAuthentication(Model model) {
        return "usercenter/crowdsourcing/auth/pspAuthentication";
    }
    /**
     * 服务商 企业认证
     */
    @RequestMapping(value = "/usercenter/epAuthentication", method = RequestMethod.GET)
    public String epAuthentication(Model model) {
        return "usercenter/crowdsourcing/auth/epAuthentication";
    }
    /**
     * 服务商 个人名片
     */
    @RequestMapping(value = "/usercenter/pBusinessCard", method = RequestMethod.GET)
    public String pBusinessCard(Model model) {
        return "usercenter/crowdsourcing/service/pBusinessCard";
    }
    /**
     * 服务商 企业名片
     */
    @RequestMapping(value = "/usercenter/eBusinessCard", method = RequestMethod.GET)
    public String eBusinessCard(Model model) {
        return "usercenter/crowdsourcing/service/eBusinessCard";
    }

}
