package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
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
            map.put("cardOwner",1);
            map.put("bankAccountType","")
           /* User user = userService.selectOne(filters);

            map.put("cardOwner",1);
            map.put("bankAccountType",payBankCard.getBankAccountType())*/;
            return ReturnData.success(map);
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
    public ReturnData addBankInfo(String cardCode, String openBank, String phoneNumber, Integer payBankId) {
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
            pay.setBindFlag(0);
            pay.setBankAccountType(1);
            pay.setPayAccountId(payAccount.getId());
            pay.setAddOperator(payAccount.getUserName());
            pay.setCardCode(cardCode);
            pay.setPhoneNumber(phoneNumber);
            pay.setOpenBank(openBank);
            pay.setPayBankId(payBankId);
            PayBankCard insert = payBankCardService.insert(pay);
            if(insert == null){
                return ReturnData.error("添加失败");
            }
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
            }
            //银行卡信息
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


}
