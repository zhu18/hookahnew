package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Region;
import com.jusfoun.hookah.rpc.api.RegionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by admin on 2017/4/5/0005.
 */

@Controller
@RequestMapping("region")
public class RegionController {

    @Resource
    RegionService regionService;

    /**
     * 获取所有省份
     * @return
     */
    @RequestMapping("/province")
    @ResponseBody
    public List<Region> getProvinces() {
        List<Region> list = regionService.getProvinces();
        return list;
    }

    /**
     * 获取所有市县 传pid共用
     * @param parentId
     * @return
     */
    @RequestMapping("/city")
    @ResponseBody
    public List<Region> getCitys(Long parentId) {
        return regionService.getCitys(parentId);
    }

}
