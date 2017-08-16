package com.jusfoun.hookah.console.server.api.funds;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.PayTradeRecordVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.PayBankCardService;
import com.jusfoun.hookah.rpc.api.PayTradeRecordService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhaoshuai
 * Created by computer on 2017/7/18.
 */

@RestController
@RequestMapping(value = "/api/platform")
public class PlatformFundsApi extends BaseController{

    @Resource
    PayBankCardService payBankCardService;

    @Resource
    PayAccountService payAccountService;

    @Resource
    PayTradeRecordService payTradeRecordService;

    @Resource
    UserService userService;

    //银行卡信息
    @RequestMapping(value = "/bankInfo", method = RequestMethod.GET)
    public ReturnData bankInfo(){
        Map<String, Object> map = new HashMap<>(6);
        try {
            String userId = this.getCurrentUser().getUserId();
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("userId", userId));
            PayBankCard payBankCard = payBankCardService.selectOne(filters);
            if(payBankCard != null){
                map.put("bankName",payBankCard.getBankName());
                map.put("cardCode",payBankCard.getCardCode());
                map.put("cardOwner",payBankCard.getCardOwner());
            }else {
                return ReturnData.error("银行卡信息不可为空");
            }
        } catch (HookahException e) {
            return ReturnData.error("查询银行卡信息失败");
        }
        return ReturnData.success(map);
    }

    //解除银行卡信息
    @RequestMapping(value = "/updateBankInfo", method = RequestMethod.GET)
    public ReturnData updateBankInfo() {
        try {
            String userId = this.getCurrentUser().getUserId();
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("userId", userId));
            PayBankCard payBankCard = payBankCardService.selectOne(filters);
            if(payBankCard != null){
                payBankCard.setBindFlag(1);
                payBankCardService.updateByIdSelective(payBankCard);
            }else {
                return ReturnData.error("银行卡信息不可为空");
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error("解除银行卡信息失败");
        }
        return ReturnData.success("银行卡信息已解除");
    }

    //账户资金
    @RequestMapping(value = "/userFunds", method = RequestMethod.GET)
    public ReturnData userFunds(){
        Map<String, Object> map = new HashMap<>(6);
        long balance = 0;
        long useBalance = 0;
        long frozenBalance = 0;
        long preDeposit = 0;
        List<PayAccount> payAccounts = payAccountService.selectList();
        for(PayAccount pay : payAccounts){
            if(pay.getId() == HookahConstants.TRADECENTERACCOUNT){
                balance = pay.getBalance();
                useBalance = pay.getUseBalance();
                frozenBalance = pay.getFrozenBalance();
            }else{
                preDeposit += pay.getBalance();
            }
        }
        map.put("balance",balance);
        map.put("useBalance",useBalance);
        map.put("freeze",frozenBalance);
        map.put("preDeposit",preDeposit);
        return ReturnData.success(map);
    }

    //资金记录
    @RequestMapping(value = "/fundsRecord", method = RequestMethod.GET)
    public ReturnData fundsRecord(String currentPage, String pageSize,String startDate, String endDate,Integer tradeType, Integer tradeStatus){
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

            //费用科目
            if (tradeType != null) {
                filters.add(Condition.eq("tradeType", tradeType));
            }else {
                //只查询的费用科目 冻结划入  释放划出  手续费收入 退款 提现
                filters.add(Condition.in("tradeType", new Integer[]{6003, 6004, 3007, 8, 2, 5}));
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
            logger.error("查询错误", e);
            return ReturnData.error("查询错误");
        }
    }

    //资金流水记录
    @RequestMapping(value = "/flowWater", method = RequestMethod.GET)
    public ReturnData flowWater(String currentPage, String pageSize, String startDate, String endDate, Integer tradeType, Integer tradeStatus){
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<PayTradeRecordVo> page = new Pagination<>();
        try {
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            List<Condition> filters = new ArrayList();

            if (tradeType != null) {
                filters.add(Condition.eq("tradeType", tradeType));
            }else {
                //只查询的费用科目
                filters.add(Condition.in("tradeType", new Integer[]{1, 2, 5, 6003, 6004, 3007, 3001, 4001, 8}));
            }
            if (tradeStatus != null) {
                filters.add(Condition.eq("tradeStatus", tradeStatus));
            }

            if (StringUtils.isNotBlank(startDate)) {
                filters.add(Condition.ge("addTime", startDate));
            }
            if (StringUtils.isNotBlank(endDate)) {
                filters.add(Condition.le("addTime", DateUtils.transferDate(endDate)));
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
            page = payTradeRecordService.getFlowListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            returnData.setData(page);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统出错，请联系管理员！");
            e.printStackTrace();
        }
        return returnData;
    }

}
