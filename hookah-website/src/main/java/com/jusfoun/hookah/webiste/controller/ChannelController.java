package com.jusfoun.hookah.webiste.controller;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.core.domain.ChannelTransData;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.ChannelService;
import com.jusfoun.hookah.webiste.util.ABCKBaoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ctp on 2017/8/29.
 */

@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Resource
    ChannelService channelService;

    @RequestMapping(value = "/push/goods")
    public ReturnData pushGoods(@RequestBody String channelTransData){
        ChannelTransData channelTransData1 = JSON.parseObject(channelTransData, ChannelTransData.class);
        return channelService.acceptGoods(channelTransData1);
    }
}
