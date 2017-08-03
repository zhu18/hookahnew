package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.FundRecordService;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class FundRecordController extends BaseController {

    @Resource
    private FundRecordService fundRecordService;

    @Resource
    PayAccountService payAccountService;

    @RequestMapping(value = "/fund/pageData", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData findByPage(Integer pageNumber, Integer pageSize, Integer tradeType, Integer tradeStatus, String startDate, String endDate) {
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

            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("userId", userId));
            PayAccount payAccount = payAccountService.selectOne(filters);
            if(payAccount == null){
                logger.error("获取资金账户信息有误");
                return ReturnData.error("获取资金账户信息有误");
            }
            listFilters.add(Condition.eq("payAccountId", payAccount.getId()));

            //查询列表
            List<OrderBy> orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("addTime"));
            Pagination<PayTradeRecord> records = fundRecordService.getDetailListInPage(pageNumber, pageSize, listFilters, orderBys);

            logger.info(JsonUtils.toJson(records));
            return ReturnData.success(records);
        } catch (Exception e) {
            logger.error("分页查询资金记录错误", e);
            return ReturnData.error("分页查询错误");
        }
    }

}
