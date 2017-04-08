package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.IndustryCode;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.rpc.api.IndustryCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/7/0007.
 */
@Controller
@RequestMapping("industryCode")
public class IndustryCodeController {

    @Resource
    IndustryCodeService industryCodeService;

    @RequestMapping(value = "/getListByPCode", method = RequestMethod.GET)
    @ResponseBody
    public List<IndustryCode> getProvinces(String parentCode) {

        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("parentIndustryCode", parentCode));
        List<IndustryCode> list = (List) industryCodeService.selectList(filters);
        return list;
    }
}
