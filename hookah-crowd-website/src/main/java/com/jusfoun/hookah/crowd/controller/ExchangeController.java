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
public class ExchangeController{

    @RequestMapping(value = "/crowdsourcingRelease", method = RequestMethod.GET)
    public String crowdsourcingRelease(Model model) {
        return "usercenter/crowdsourcing/release";
    }

    @RequestMapping(value = "/crowdsourcing-list", method = RequestMethod.GET)
    public String crowdsourcingList(Model model) {
        return "crowdsourcing/index";
    }



}
