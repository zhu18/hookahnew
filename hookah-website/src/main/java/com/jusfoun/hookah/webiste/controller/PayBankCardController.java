package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.FormatCheckUtil;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.PayBankCardService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.collections.map.HashedMap;
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
     * 绑定银行卡信息
     * @param payBankCard
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBankInfo", method = RequestMethod.GET)
    public ReturnData addBankInfo(PayBankCard payBankCard) {
        try {
            String userId = this.getCurrentUser().getUserId();
            List<Condition> filters = new ArrayList<>();
            if(StringUtils.isNotBlank(userId)){
                filters.add(Condition.eq("userId", userId));
            }
            User user = userService.selectOne(filters);
            PayAccount payAccount = payAccountService.selectOne(filters);
            //查询实名认证的企业名称
            if(StringUtils.isNotBlank(user.getOrgId())){
                Organization organization = organizationService.selectById(user.getOrgId());
                PayBankCard pay= new PayBankCard();
                //对公银行账户
                pay.setBankAccountType(1);
                //企业名称
                pay.setCardOwner(organization.getOrgName());
                pay.setUserId(user.getUserId());
                pay.setAddTime(new Date());
                pay.setBindFlag(0);
                pay.setBankAccountType(1);
                pay.setPayAccountId(payAccount.getId());
                pay.setAddOperator(payAccount.getUserName());
                pay.setCardCode(payBankCard.getCardCode());
                pay.setPhoneNumber(payBankCard.getPhoneNumber());
                pay.setBankName(payBankCard.getBankName());
                /*String reg = "^\\d{8}$";
                String re = "^\\d{16}$";
                if(!FormatCheckUtil.checkMobile(payBankCard.getPhoneNumber())&& !payBankCard.getPhoneNumber().matches(reg)){
                    throw new HookahException("格式错误，请输入正确的手机号");
                }else if(!payBankCard.getCardCode().matches(re)){
                    throw new HookahException("请输入正确的银行卡号");
                }*/
                payBankCard = payBankCardService.insert(payBankCard);
                if(payBankCard == null){
                    return ReturnData.error("添加失败");
                }
                return ReturnData.success(payBankCard);
            }else {
                return ReturnData.success("企业未认证");
            }
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
            }else{
                return ReturnData.error("账户信息为空");
            }
            //银行卡信息
            PayBankCard payBankCard = payBankCardService.selectOne(filters);
            if(payBankCard != null){
                map.put("bankName",payBankCard.getBankName());
                map.put("cardCode",payBankCard.getCardCode());
                map.put("cardOwner",payBankCard.getCardOwner());
            }else {
                return ReturnData.error("银行卡信息不可为空");
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
