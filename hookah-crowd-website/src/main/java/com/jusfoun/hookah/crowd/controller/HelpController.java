package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.Help;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huang lei
 * @date 2017/4/10 下午1:47
 * @desc
 */
@Controller
@RequestMapping("/help")
public class HelpController extends BaseController{


    @RequestMapping(value = "/{id}.html", method = RequestMethod.GET)
    public String index(@PathVariable("id") String id, Model model) {
//        Map<String,Object> result = new HashMap<String,Object>();
//        List<Help> helpList = helpService.selectList();
//        result.put("helpList",helpList);
//        Help help = helpService.selectById(id);
//        result.put("help",help);
//        model.addAttribute("result",result);
        return "/help/help_index";
    }
    @RequestMapping(value = "/privacy", method = RequestMethod.GET)
    public String privacy() {
        return "/help/privacyStatement";
    }

    @RequestMapping(value = "/buyers_guide", method = RequestMethod.GET)
    public String buyers() {
        return "/help/buyers_guide";
    }
    @RequestMapping(value = "/arrival_guide", method = RequestMethod.GET)
    public String arrival () {
        return "/help/arrival_guide";
    }

    @RequestMapping(value = "/resource_cooperation", method = RequestMethod.GET)
    public String resource () {
        return "/help/resource_cooperation";
    }

    @RequestMapping(value = "/brand_cooperation", method = RequestMethod.GET)
    public String brand () {
        return "/help/brand_cooperation";
    }

    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public String question() {
        return "/help/question";
    }

    @RequestMapping(value = "/agreement", method = RequestMethod.GET)
    public String agreement() {
        return "/help/service_agreement";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "/help/about";
    }



    @ResponseBody
    @RequestMapping(value = "/helpApi/search", method = RequestMethod.GET)
    public ReturnData helpApiSearch() {
        return ReturnData.success("");
    }
}
