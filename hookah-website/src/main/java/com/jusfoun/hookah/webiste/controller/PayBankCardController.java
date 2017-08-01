package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.constants.PayConstants;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.PayBankCardService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author ZHAOSHUAI
 * Created by computer on 2017/7/12.
 */

@Controller
@RequestMapping("/payBankCard")
public class PayBankCardController extends BaseController{
    protected final static Logger logger = LoggerFactory.getLogger(PayController.class);

    @Resource
    PayBankCardService payBankCardService;

    @Resource
    PayAccountService payAccountService;

    @Resource
    UserService userService;

    @Resource
    OrganizationService organizationService;

    /**
     * 查询银行信息  银行账户类型 开户行
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/searchBankInfo", method = RequestMethod.GET)
    public ReturnData searchBankInfo(){
        try {
            String userId = this.getCurrentUser().getUserId();
            Map<String, Object> map = new HashMap<>(6);
            List<PayBank> banks = payBankCardService.selectBankName();
            map.put("bank",banks);
            List<Condition> filters = new ArrayList<>();
            if(StringUtils.isNotBlank(userId)){
                filters.add(Condition.eq("userId", userId));
            }
            User user = userService.selectOne(filters);
            if(StringUtils.isNotBlank(user.getOrgId())){
                Organization organization = organizationService.selectById(user.getOrgId());
                if(StringUtils.isNotBlank(organization.getOrgName())){
                    map.put("cardOwner",organization.getOrgName());
                    map.put("bankAccountType",1);
                }
                return ReturnData.success(map);
            }else{
                return ReturnData.error("该用户未认证");
            }
        } catch (HookahException e) {
            logger.error("查询失败",e);
            return ReturnData.error("查询失败");
        }
    }


    /**
     * 绑定银行卡信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBankInfo", method = RequestMethod.GET)
    public ReturnData addBankInfo(String cardOwner, String cardCode, String openBank, String phoneNumber, Integer payBankId,Integer bankAccountType) {
        try {
            String userId = this.getCurrentUser().getUserId();
            List<Condition> filters = new ArrayList<>();
            if(StringUtils.isNotBlank(userId)){
                filters.add(Condition.eq("userId", userId));
            }
            PayAccount payAccount = payAccountService.selectOne(filters);
            PayBankCard pay= new PayBankCard();
            pay.setUserId(userId);
            pay.setAddTime(new Date());
            pay.setCardOwner(cardOwner);
            pay.setBindFlag(PayConstants.BankCardStatus.binded.getCode());
            pay.setBankAccountType(bankAccountType);
            if(payAccount != null){
                pay.setPayAccountId(payAccount.getId());
                pay.setAddOperator(payAccount.getUserName());
            }
            pay.setCardCode(cardCode);
            pay.setPhoneNumber(phoneNumber);
            pay.setOpenBank(openBank);
            pay.setPayBankId(payBankId);
            PayBankCard insert = payBankCardService.insert(pay);
            return ReturnData.success(insert);
        }catch (Exception e){
            logger.error("绑定银行卡失败",e);
            return ReturnData.error("绑定银行卡失败");
        }
    }

    /**
     * 查询账户资金 银行卡信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userFunds", method = RequestMethod.GET)
    public ReturnData userFunds(){
        Map<String, Object> map = new HashMap<>(6);
        try {
            String userId = this.getCurrentUser().getUserId();
            List<Condition> filters = new ArrayList();
            if(StringUtils.isNotBlank(userId))
                filters.add(Condition.eq("userId", userId));
            User user = userService.selectOne(filters);
            if(user!=null)
                map.put("userName",user.getUserName());
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
            }
            //银行卡信息
            filters.add(Condition.eq("bindFlag", PayConstants.BankCardStatus.binded.getCode()));
            PayBankCard payBankCard = payBankCardService.selectOne(filters);
            if(payBankCard != null){
                map.put("bankId",payBankCard.getPayBankId());
                map.put("cardCode",payBankCard.getCardCode());
                map.put("cardOwner",payBankCard.getCardOwner());
                map.put("bindFlag",payBankCard.getBindFlag());
            }
            return ReturnData.success(map);
        } catch (HookahException e) {
            logger.info(e.getMessage());
            return ReturnData.error("查询账户资金失败");
        }
    }


    /**
     * 解绑银行卡信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateBankInfo", method = RequestMethod.GET)
    public ReturnData updateBankInfo(){
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("userId", userId));
            //银行卡状态  正常
            filters.add(Condition.eq("bindFlag",PayConstants.BankCardStatus.binded.getCode()));
            PayBankCard payBankCard = payBankCardService.selectOne(filters);
            payBankCard.setBindFlag(PayConstants.BankCardStatus.unbind.getCode());
            int n = payBankCardService.updateByIdSelective(payBankCard);
            if(n == 1){
                returnData.setMessage("银行卡信息已解除");
            }else{
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("解除银行卡信息失败！");
            }

        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error("解除银行卡信息失败");
        }
        return returnData;
    }


}
