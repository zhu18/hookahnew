package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Cooperation;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CooperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lt on 2017/6/2.
 */
@RestController
@RequestMapping("/coo")
public class CooperationController {
    private static final Logger logger = LoggerFactory.getLogger(CooperationController.class);

    @Resource
    private CooperationService cooperationService;

    @RequestMapping("/searchAll")
    public ReturnData search(Cooperation cooperation){
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));
            filters.add(Condition.eq("state", cooperation.COOPERATION_STATE_ON));
            List<Cooperation> list = cooperationService.selectList(filters);
            return ReturnData.success(list);
        }catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error("获取失败");
        }
    }
}
