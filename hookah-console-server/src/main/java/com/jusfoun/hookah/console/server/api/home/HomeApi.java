package com.jusfoun.hookah.console.server.api.home;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.annotation.Log;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.HomeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by ctp on 2017/6/15.
 */
@RestController
@RequestMapping("/api/home")
public class HomeApi extends BaseController{

    @Resource
    HomeService homeService;
    @Log(platform = "back",logType = "b001",optType = "insert")
    @RequestMapping("/init")
    public ReturnData init(){
        return  homeService.init();
    }

}
