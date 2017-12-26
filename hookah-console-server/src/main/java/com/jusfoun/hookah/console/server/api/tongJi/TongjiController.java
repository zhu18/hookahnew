package com.jusfoun.hookah.console.server.api.tongJi;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.FlowUserService;
import com.jusfoun.hookah.rpc.api.TongJiInfoService;
import com.jusfoun.hookah.rpc.api.TransactionAnalysisService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by crs on 2017/12/1.
 */
@RestController
@RequestMapping(value = "/api/tongji")
public class TongjiController extends BaseController {
    @Resource
    private FlowUserService flowUserService;

    @Resource
    TongJiInfoService   tongJiInfoService;

    @Resource
    TransactionAnalysisService transactionAnalysisService;

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

    @RequestMapping(value = "/countOrderInfo")
    public ReturnData countOrderInfo(String startTime, String endTime){
        if(!StringUtils.isNotBlank(startTime)){
            startTime=  LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if(!StringUtils.isNotBlank(endTime)){
            endTime=  LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        ReturnData returnData = transactionAnalysisService.countOrderList(startTime, endTime);
        return returnData;
    }

    @RequestMapping(value = "/tongJiiii")
    public void tongJi() {
       tongJiInfoService.saveTongJiInfoService();
//        return returnData;
    }

    @RequestMapping(value = "/countOrderRightNow")
    public ReturnData tongJiOrder() {
//       tongJiInfoService.countOrderData();
        return ReturnData.success();
    }


}
