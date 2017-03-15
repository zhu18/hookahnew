package com.jusfoun.hookah.webiste.controller;


import com.jusfoun.hookah.core.domain.SysNews;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.rpc.api.SysNewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻资讯类接口
 */
@Controller
public class SysNewsController {


    @Resource
    SysNewsService sysNewsService;

   /* @RequestMapping(value = "/sysNews/list", method = RequestMethod.POST)
    public String list(SysNews model){
        sysNewsService.insert(model);
        return "/goods/list";
    }*/

    @RequestMapping(value = "/sysNews/details", method = RequestMethod.GET)
    public String details(){
        return "/goods/details";
    }

 /*   @RequestMapping(value = "/sysNews/getModelById", method = RequestMethod.GET)
    public String getModelById(){
        SysNews model = new SysNews();
        String id = "123";
        model = sysNewsService.selectById(id);
         System.out.print(model.getContent() +" --------------------------------------------------  ");
        return "/goods/details";
    }*/


    @RequestMapping(value = "/sysNews/insert", method = RequestMethod.GET)
    @ResponseBody
    public Object insert(Model model) {
        SysNews t2 = new SysNews();
        t2.setContent("dfdfsdfsfsfsdfsdf");
        sysNewsService.insert(t2);
        return "success";
    }


    @RequestMapping(value = "/sysNews/select", method = RequestMethod.GET)
    @ResponseBody
    public Object select(Model model) {
        List<Condition> filters = new ArrayList();
     /*   filters.add(Condition.eq("produceType", paramMap.get("produceType")));*/

        List<SysNews> list = (List) sysNewsService.selectList();
        return list;
    }
}
