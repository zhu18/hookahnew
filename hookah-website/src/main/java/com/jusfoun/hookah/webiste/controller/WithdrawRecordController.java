package com.jusfoun.hookah.webiste.controller;


import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.domain.WithdrawRecord;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.PayBankCardService;
import com.jusfoun.hookah.rpc.api.WithdrawRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 提现
 */

@Controller
@RequestMapping("/withdrawRecord")
public class WithdrawRecordController extends BaseController{

    @Resource
    WithdrawRecordService withdrawRecordService;

    @Resource
    PayAccountService payAccountService;

    @Resource
    PayBankCardService payBankCardService;

    /**
     * 发起提现申请
     */
    @RequestMapping("/applyW")
    @ResponseBody
    public ReturnData apply(WithdrawRecord withdrawRecord) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            withdrawRecord.setUserId(getCurrentUser().getUserId());
            returnData = withdrawRecordService.applyWithdraw(withdrawRecord);

        } catch (HookahException e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统出错，请联系管理员！");
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 查询申请列表
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ReturnData getList(String startDate, String endDate,
            String currentPage, String pageSize, String checkStatus
    ) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        Pagination<WithdrawRecord> page = new Pagination<>();
        try {

            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));

            List<Condition> filters = new ArrayList();


            filters.add(Condition.eq("userId", getCurrentUser().getUserId()));

            if(checkStatus != null){
                filters.add(Condition.eq("checkStatus", checkStatus));
            }

            if(StringUtils.isNotBlank(startDate)){
                filters.add(Condition.ge("addTime", startDate));
            }

            if(StringUtils.isNotBlank(endDate)){
                filters.add(Condition.le("addTime", endDate));
            }

            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }

            page = withdrawRecordService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

            returnData.setData(page);
        } catch (HookahException e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统出错，请联系管理员！");
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 返回个人账户信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public String userInfo(Model model) {

        try {
            Map<String, Object> map = new HashMap();

            List<Condition> bankFilters = new ArrayList();
            bankFilters.add(Condition.eq("userId", getCurrentUser().getUserId()));
            bankFilters.add(Condition.eq("bindlag", 0));
            PayBankCard payBankCard = payBankCardService.selectOne(bankFilters);
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("userId", getCurrentUser().getUserId()));
            PayAccount payAccount = payAccountService.selectOne(filters);
            if(payAccount != null && payBankCard!= null){
                payBankCard.setPayAccountId(payAccount.getUseBalance());
            }

            model.addAttribute("payBankCard", payBankCard);
        } catch (HookahException e) {
            e.printStackTrace();
        }
        return "/usercenter/userInfo/withdrawals";
    }


    /**
     * 查看提现状态
     * @param id
     * @return
     */
    @RequestMapping(value = "/getWithDrawStatus", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData userInfo(@RequestParam("id") Long id) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        try {
            List<Condition> cashFilters = new ArrayList();
            cashFilters.add(Condition.eq("id", id));
            WithdrawRecord withdrawRecord = withdrawRecordService.selectOne(cashFilters);

            if(withdrawRecord == null){
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("未查询到此条记录，如有疑问请联系管理员");
                return returnData;
            }

            returnData.setData(withdrawRecord);

        } catch (Exception e) {
            logger.error(e.getMessage());
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统异常");
            return returnData;
        }
        return returnData;
    }


    public static void main(String[] args){

        System.out.println(LocalDateTime.now().toString());
        System.out.println(LocalDateTime.now().getNano());
        System.out.println(Instant.now());
        System.out.println(System.nanoTime());
        System.out.println(new Date().toString());
        System.out.println(Thread.currentThread().hashCode());
        System.out.println(DateUtils.getCurrentTimeFormat(new Date()));
        System.out.println(DateUtils.getCurrentTimeFormat(new Date()) + Thread.currentThread().getId());
    }
}
