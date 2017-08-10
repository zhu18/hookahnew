package com.jusfoun.hookah.console.server.api.userFoun;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.vo.UserFundCritVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.PayTradeRecordService;
import com.jusfoun.hookah.rpc.api.UserFundService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ctp on 2017/7/18.
 */
@RestController
@RequestMapping(value = "/api/userFund")
public class UserFundApi extends BaseController{

    @Resource
    UserFundService userFundService;

    @Resource
    PayTradeRecordService payTradeRecordService;

    /**
     *
     */
    @RequestMapping(value = "/all")
    public ReturnData searchUserFund(UserFundCritVo userFundCritVo){
        return userFundService.getUserFundListPage(userFundCritVo);
    }

    /**
     * 用户资金记录详情
     * @param
     * @return
     */
    @RequestMapping(value = "/userFundDetail")
    public ReturnData searchUserFundDetail(String userId,String currentPage, String pageSize,String startDate, String endDate,Integer tradeType, Integer tradeStatus){
        Pagination<PayTradeRecord> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));

            if (StringUtils.isNotBlank(startDate)) {
                filters.add(Condition.ge("addTime", DateUtils.getDate(startDate, DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if (StringUtils.isNotBlank(endDate)) {
                filters.add(Condition.le("addTime", DateUtils.getDate(endDate, DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if(StringUtils.isNotBlank(userId)){
                filters.add(Condition.eq("userId",userId));
            }

            //只查询的费用科目 充值  提现  销售（货款）收入 销售（货款）支出  冲账 退款 其他
            filters.add(Condition.in("tradeType", new Integer[]{1, 2, 3001, 4001, 8}));
            //费用科目
            if (tradeType != null) {
                filters.add(Condition.eq("tradeType", tradeType));
            }
            //状态
            if (tradeStatus != null) {
                filters.add(Condition.eq("tradeStatus", tradeStatus));
            }
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = payTradeRecordService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            return ReturnData.success(page);
        } catch (Exception e) {
            logger.error("分页查询资金记录错误", e);
            return ReturnData.error("查询错误");
        }
    }

}
