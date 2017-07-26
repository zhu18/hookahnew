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
        try {
            String userId = this.getCurrentUser().getUserId();
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("userId", userId));
            PayAccount payAccount = payAccountService.selectOne(filters);
            if(payAccount != null){
                //账户余额
                map.put("balance",payAccount.getBalance());
                //可用金额
                map.put("useBalance",payAccount.getUseBalance());
                //冻结金额 = 账户余额 -  可用金额
                long freeze = 0;
                freeze = payAccount.getBalance() - payAccount.getUseBalance();
                map.put("freeze",freeze);
                //客户预存款
                List<PayAccount> payAccounts = payAccountService.selectList();
                long preDeposit = 0;
                for (PayAccount pay:payAccounts){
                    if(pay.getUseBalance() != null){
                        preDeposit += pay.getUseBalance();
                    }else {
                        continue;
                    }
                }
                map.put("preDeposit",preDeposit);
            }else{
                return ReturnData.error("账户信息为空");
            }
        } catch (HookahException e) {
            logger.info(e.getMessage());
            return ReturnData.error("查询账户资金失败");
        }
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

            //只查询的费用科目 冻结划入  释放划出  手续费收入 退款
            filters.add(Condition.in("tradeType", new Integer[]{6003, 6004, 3007, 8}));
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
            logger.error("查询错误", e);
            return ReturnData.error("查询错误");
        }
    }

    //资金流水记录
    @RequestMapping(value = "/flowWater", method = RequestMethod.GET)
    public ReturnData flowWater(String currentPage, String pageSize, String startDate, String endDate, Integer tradeType, Integer tradeStatus){

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<PayTradeRecordVo> pagination = new Pagination<>();
        Pagination<PayTradeRecord> page = new Pagination<>();
        try {

            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));

            List<Condition> filters = new ArrayList();

            if (tradeType != null) {
                filters.add(Condition.eq("tradeType", tradeType));
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

            page = payTradeRecordService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
            List<PayTradeRecordVo> listVo = new ArrayList<>();
            if(page.getList() != null && page.getList().size() > 0){

                page.getList().parallelStream().forEach(x -> {

                    PayTradeRecordVo vo = new PayTradeRecordVo();

                    if(x.getTradeType().equals(3007) ||
                            x.getTradeType().equals(6003) ||
                            x.getTradeType().equals(6004)){
                        vo.setAccountParty("交易中心平台资金");
                    }else{
                        User user = userService.selectById(x.getUserId());
                        if(user != null){
                            vo.setAccountParty((user.getUserName() == null ? "会员" : user.getUserName()) + "平台资金");
                        }else{
                            vo.setAccountParty("会员平台资金");
                        }
                    }
                    BeanUtils.copyProperties(x, vo);
                    listVo.add(vo);
                });
            }
            pagination.setTotalItems(page.getTotalItems());
            pagination.setPageSize(pageSizeNew);
            pagination.setCurrentPage(pageNumberNew);
            pagination.setList(listVo);
            returnData.setData(pagination);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统出错，请联系管理员！");
            e.printStackTrace();
        }
        return returnData;
    }

}
