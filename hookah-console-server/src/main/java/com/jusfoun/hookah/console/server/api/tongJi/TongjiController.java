package com.jusfoun.hookah.console.server.api.tongJi;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.FlowUserService;
import com.jusfoun.hookah.rpc.api.TongJiInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by admin on 2017/12/1.
 */
@RestController
@RequestMapping(value = "/tongji")
public class TongjiController extends BaseController {
    @Resource
    private FlowUserService flowUserService;

    @Resource
    TongJiInfoService   tongJiInfoService;

    @RequestMapping(value = "/reqUser")
    public ReturnData tongjiList(String startTime,String endTime){
        if(!StringUtils.isNotBlank(startTime)){
            startTime=  LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if(!StringUtils.isNotBlank(endTime)){
            endTime=  LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        ReturnData returnData = flowUserService.tongjiList(startTime, endTime);
        return returnData;
    }

    @RequestMapping(value = "/tongJiiii")
    public ReturnData tongJi() {
        ReturnData returnData =   tongJiInfoService.saveTongJiInfoService();
        return returnData;
    }
}
