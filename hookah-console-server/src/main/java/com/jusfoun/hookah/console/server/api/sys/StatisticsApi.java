package com.jusfoun.hookah.console.server.api.sys;

import com.jusfoun.hookah.core.domain.Region;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import com.jusfoun.hookah.rpc.api.RegionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计API
 */
@RestController
@RequestMapping(value = "/api/sys/statistics")
public class StatisticsApi {

    @Resource
    OrderInfoService orderInfoService;

    @RequestMapping(value = "/orderData", method = RequestMethod.GET)
    public ReturnData getListInPage(String currentPage, String pageSize,
                                    String goodsName, String goodsSn) {
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map = orderInfoService.getStatistics();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(map);
    }
}
