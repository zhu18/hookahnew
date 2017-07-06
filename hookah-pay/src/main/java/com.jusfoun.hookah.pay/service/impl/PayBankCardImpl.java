package com.jusfoun.hookah.pay.service.impl;

import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.bean.ResultBean;
import com.apex.fix.AxCallFunc;
import com.apex.fix.JFixComm;
import com.apex.fix.JFixSess;
import com.jusfoun.hookah.core.dao.PayBankCardMapper;
import com.jusfoun.hookah.core.domain.PayAccountRecord;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.pay.util.*;
import com.jusfoun.hookah.rpc.api.PayAccountRecordService;
import com.jusfoun.hookah.rpc.api.PayBankCardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PayBankCardImpl extends GenericServiceImpl<PayBankCard, String> implements PayBankCardService {

    @Resource
    private PayBankCardMapper payBankCardMapper;

    @Resource
    private PayAccountRecordService payAccountRecordService;

    @Resource
    FixClientUtil client;

    private IFixClient fixClient = client.createClientSSL();

    @Resource
    public void setDao(PayBankCardMapper payBankCardMapper) {
        super.setDao(payBankCardMapper);
    }

    /**
     * @param userId        用户id
     * @param customerNum   客户号
     * @param bankName      银行名称
     * @param bankCardNum   银行卡号
     * @param bankCardOwner 持卡人
     * @param ip            ip地址
     * @param ukey          k宝信息
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public boolean bankCardSignOn(String userId, String customerNum, String bankName, String bankCardNum, String bankCardOwner, String ip, String ukey) {
        //根据userId查询accountId
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("userId", userId));
        PayAccountRecord payAccountRecord = payAccountRecordService.selectOne(filters);
        // 组装参数
        Map<String, String> paramMap = buildSignOnParam(customerNum, bankCardNum, bankCardOwner, ip, ukey);
        //构造数据库对象
        PayBankCard payBankCard = new PayBankCard();
        //插入记录
        payBankCard.setPayAccountId(payAccountRecord.getPayAccountId());
        payBankCard.setUserId(userId);
        payBankCard.setBankName(bankName);
        payBankCard.setBankCode(PayConstants.BankCode.NY02.code);
        payBankCard.setCardOwner(bankCardOwner);
        payBankCard.setCardCode(bankCardNum);
        payBankCard.setAddOperator(userId);
        payBankCard.setAddTime(new Date());
        // 发起请求
        ResultBean<Map<String, String>> resultBean = this.fixClient.sendFirmSignOn(paramMap, new AxCallFunc() {
            public boolean onReply(JFixSess jFixSess, JFixComm jFixComm) {
                //回调函数处理
                return signOnReply(jFixSess, jFixComm, payBankCard);
            }
        });
        if (resultBean.isSuccess()) {
            //TODO 发送成功，插入mongo

        } else {
            //发送失败
            return false;
        }
        return true;
    }

    private boolean signOnReply(JFixSess jFixSess, JFixComm jFixComm, PayBankCard payBankCard) {
        try {
            if (jFixSess.getCount() > 0) {
                jFixSess.go(0);
                if (jFixSess.getCode() > 0) {
                    //成功后插入数据
                    payBankCard.setBindFlag(PayConstants.BankCardStatus.binded.code);
                    payBankCardMapper.insert(payBankCard);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * @param id            主键
     * @param userId        用户id
     * @param customerNum   客户号
     * @param bankCardNum   银行卡号
     * @param bankCardOwner 持卡人
     * @param ip            IP地址
     * @param ukey          k宝信息
     * @return
     */
    @Override
    public boolean bankCardSignOff(Integer id, String userId, String customerNum, String bankCardNum, String bankCardOwner, String ip, String ukey) {
        // 组装参数
        Map<String, String> paramMap = buildSignOnParam(customerNum, bankCardNum, bankCardOwner, ip, ukey);
        paramMap.remove("FID_YHZHMC");
        paramMap.remove("FID_WDH");
        paramMap.remove("FID_MOBILE");
        //构造数据库对象
        PayBankCard payBankCard = new PayBankCard();
        payBankCard.setId(id);
        payBankCard.setBindFlag(PayConstants.BankCardStatus.unbind.code);
        payBankCard.setUpdateOperator(userId);
        payBankCard.setUpdateTime(new Date());
        // 发起请求
        ResultBean<Map<String, String>> resultBean = this.fixClient.sendFirmSignOff(paramMap, new AxCallFunc() {
            public boolean onReply(JFixSess jFixSess, JFixComm jFixComm) {
                //回调函数处理
                return signOffReply(jFixSess, jFixComm, payBankCard);
            }
        });
        if (resultBean.isSuccess()) {
            //TODO 发送成功，插入mongo

        } else {
            //发送失败
            return false;
        }
        return true;
    }

    private boolean signOffReply(JFixSess jFixSess, JFixComm jFixComm, PayBankCard payBankCard) {
        try {
            if (jFixSess.getCount() > 0) {
                jFixSess.go(0);
                if (jFixSess.getCode() > 0) {
                    //成功后更新数据
                    payBankCardMapper.updateByPrimaryKeySelective(payBankCard);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private Map<String, String> buildSignOnParam(String customerNum, String accountNum, String bankCardOwner, String ip, String ukey) {
        Map<String, String> paramMap = new HashMap<>();
        // 获取交易市场申请号
        String serialNum = PayUtil.createChannelSerial(ChannelType.QDABC);

        paramMap.put("FID_JYS", PayConstants.FID_JYS);//交易市场*
        paramMap.put("FID_BZ", PayConstants.QD_BZ);//币种*
        paramMap.put("FID_SQRQ", DateUtil.getCurrentDate("YYYYMMDD"));//申请日期
        paramMap.put("FID_SQSJ", DateUtil.getCurrentTime("HH24:MI:SS"));//申请时间
        paramMap.put("FID_SQH", serialNum);//交易市场申请号*
        paramMap.put("FID_ZJZH", customerNum);//资金账号（客户号）*
        paramMap.put("FID_YHZHMC", bankCardOwner);//银行账户名称*
        paramMap.put("FID_YHZH", accountNum);//银行账户*
        paramMap.put("FID_YHDM", PayConstants.BankCode.NY02.getCode());//银行代码*
        paramMap.put("FID_YHMM", "");//银行密码
        paramMap.put("FID_CZZD", ip);//操作站点
        paramMap.put("FID_WDH", "");//银行网点号
        paramMap.put("FID_MOBILE", "");//手机号
        paramMap.put("FID_BZXX", "");//备注信息
        paramMap.put("FID_ZHYE", "");//账号余额
        paramMap.put("FID_CS1", ukey);//预留参数1（k宝返回）*
        paramMap.put("FID_CS2", PayConstants.QDABC_PREFIX + ukey);//预留参数2（BD + k宝返回）*
        return paramMap;
    }
}