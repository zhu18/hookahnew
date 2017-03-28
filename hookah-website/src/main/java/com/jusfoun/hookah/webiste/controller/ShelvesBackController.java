package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.GoodsShelves;
import com.jusfoun.hookah.core.domain.vo.OptionalShelves;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.ShelvesService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangjl on 2017-3-23.
 */
@RestController
@RequestMapping("shelves/back")
public class ShelvesBackController {
    @Resource
    ShelvesService shelvesService;
    /**
     * 查询用户可选的货架
     * @return
     */
    @RequestMapping("optional")
    public ReturnData getOptionShelves() {
        // TODO: 2017-3-24
        OptionalShelves optionalShelves = shelvesService.getOptionShelves();
        return null;
    }

    @RequestMapping("add")
    public ReturnData addShopShelves(@RequestBody GoodsShelves obj) {
        return null;
    }

}
