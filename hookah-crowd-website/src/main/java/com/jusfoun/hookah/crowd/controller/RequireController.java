package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/require")
public class RequireController extends BaseController{

    @Resource
    ZbRequireService zbRequireService;

    @RequestMapping("/insertRequire")
    public void insertRequire(){

        ZbRequirement zb = new ZbRequirement();
        zb.setTitle("123456");
        zbRequireService.insertRecord(zb);

        try {
            String Uname = this.getCurrentUser().getUserName();
            System.out.print(Uname);
        } catch (HookahException e) {
            e.printStackTrace();
        }
    }


}
