package com.jusfoun.hookah.console.server.api.advert;

import com.jusfoun.hookah.rpc.api.AdvertService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/4/13 下午1:50
 * @desc
 */
@RestController
@RequestMapping(value = "/api/advert")
public class AdvertApi {

    @Resource
    private AdvertService advertService;


}
