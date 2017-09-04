package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.ChannelTransData;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.ChannelService;
import com.jusfoun.hookah.webiste.util.ABCKBaoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ctp on 2017/8/29.
 */

@Controller
@RequestMapping("/channel")
public class ChannelController {

    @Resource
    ChannelService channelService;

    @RequestMapping(value = "/push/goods")
    public ReturnData pushGoods(@RequestBody ChannelTransData channelTransData){
        return channelService.acceptGoods(channelTransData);
    }
}
