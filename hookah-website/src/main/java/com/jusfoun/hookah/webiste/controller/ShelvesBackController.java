package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.utils.ReturnData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangjl on 2017-3-23.
 */
@RestController
@RequestMapping("shelves/back")
public class ShelvesBackController {
    /**
     * 查询用户可选的货架
     * @return
     */
    @RequestMapping("optional")
    public ReturnData getOptionShelves() {
        return null;
    }
}
