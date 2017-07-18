package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.OrderHelper;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class FundRecordController extends BaseController {
    @Autowired
    private FundRecordService fundRecordService;

    @RequestMapping(value = "/fund/pageData", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData findByPage(Integer pageNumber, Integer pageSize, Integer tradeType, Integer tradeStatus, String startDate, String endDate) {
        Map map = new HashMap<>();
        try {
            String userId = this.getCurrentUser().getUserId();

            if (pageNumber == null) pageNumber = Integer.parseInt(PAGE_NUM);
            if (pageSize == null) pageSize = Integer.parseInt(PAGE_SIZE);

            List<Condition> listFilters = new ArrayList<>();
            if (StringUtils.isNotBlank(startDate)) {
                listFilters.add(Condition.ge("addTime", DateUtils.getDate(startDate, DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if (StringUtils.isNotBlank(endDate)) {
                listFilters.add(Condition.le("addTime", DateUtils.getDate(endDate, DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if (tradeType != null) {
                listFilters.add(Condition.eq("tradeType", tradeType));
            }
            if (tradeStatus != null) {
                listFilters.add(Condition.eq("tradeStatus", tradeStatus));
            }
            listFilters.add(Condition.eq("userId", userId));

            //查询列表
            List<OrderBy> orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("addTime"));
            Pagination<PayTradeRecord> records = fundRecordService.getDetailListInPage(pageNumber, pageSize, listFilters, orderBys);
            map.put("records", records);

            logger.info(JsonUtils.toJson(map));
            return ReturnData.success(map);
        } catch (Exception e) {
            logger.error("分页查询资金记录错误", e);
            return ReturnData.error("分页查询错误");
        }
    }

}
