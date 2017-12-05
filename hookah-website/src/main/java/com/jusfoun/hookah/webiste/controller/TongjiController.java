package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.dao.FlowUserMapper;
import com.jusfoun.hookah.core.domain.FlowUser;
import com.jusfoun.hookah.core.domain.vo.FlowUserVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.FlowUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/12/1.
 */
@RestController
@RequestMapping(value = "/tongji")
public class TongjiController extends BaseController{
    @Resource
    private FlowUserService flowUserService;

    @RequestMapping(value = "/reqUser")
    public ReturnData tongjiList(String startTime,String endTime){
        ReturnData returnData = flowUserService.tongjiList(startTime, endTime);
;        return returnData;
    }

}
