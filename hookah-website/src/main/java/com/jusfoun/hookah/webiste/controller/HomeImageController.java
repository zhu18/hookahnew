package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Help;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.HelpService;
import com.jusfoun.hookah.rpc.api.HomeImageService;
import com.jusfoun.hookah.rpc.api.MgOrderInfoService;
import com.jusfoun.hookah.webiste.util.FreemarkerWord;
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

@RestController
@RequestMapping(value = "/api/image")
public class HomeImageController extends BaseController{
    @Resource
    HomeImageService homeImageService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData list(Byte imgType) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("imgType", imgType));
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.asc("imgSort"));
            returnData.setData(homeImageService.selectList(filters,orderBys));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

}
