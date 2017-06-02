package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Cooperation;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CooperationService;
import org.apache.commons.lang3.StringUtils;
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

    @RequestMapping("/search")
    public ReturnData search(String currentPage, String pageSize, Cooperation cooperation){
        Pagination<Cooperation> page = new Pagination<>();
        try {
            int i = 0;
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("lastUpdateTime"));

            filters.add(Condition.eq("state", cooperation.COOPERATION_STATE_ON));
            if(StringUtils.isNotBlank(cooperation.getCooName())){
                filters.add(Condition.like("cooName", cooperation.getCooName().trim()));
            }

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }

            page = cooperationService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
        }catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error("获取失败");
        }
        return ReturnData.success(page);
    }
}
