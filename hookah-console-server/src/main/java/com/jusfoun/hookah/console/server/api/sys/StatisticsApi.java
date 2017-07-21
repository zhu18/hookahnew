package com.jusfoun.hookah.console.server.api.sys;

import com.jusfoun.hookah.core.domain.Region;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.RegionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 统计API
 */
@RestController
@RequestMapping(value = "/api/sys/statistics")
public class StatisticsApi {

    @Resource
    RegionService regionService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getListInPage(String currentPage, String pageSize,
                                    String goodsName, String goodsSn) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
