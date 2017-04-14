package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Help;
import com.jusfoun.hookah.rpc.api.HelpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
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
public class HelpController {

    @Resource
    HelpService helpService;

    @RequestMapping(value = "/{id}.html", method = RequestMethod.GET)
    public String index(@PathVariable("id") String id, Model model) {
        Map<String,Object> result = new HashMap<String,Object>();
        List<Help> helpList = helpService.selectList();
        result.put("helpList",helpList);
        Help help = helpService.selectById(id);
        result.put("help",help);
        model.addAttribute("result",result);
        return "/help/help_index";
    }
}
