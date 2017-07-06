package com.jusfoun.hookah.pay.service.impl;

import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.FixConstants;
import com.apex.etm.qss.client.fixservice.bean.ResultBean;
import com.apex.fix.AxCallFunc;
import com.apex.fix.JFixComm;
import com.apex.fix.JFixSess;
import com.jusfoun.hookah.core.dao.PayBankCardMapper;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.pay.util.ChannelType;
import com.jusfoun.hookah.pay.util.DateUtil;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.pay.util.PayUtil;
import com.jusfoun.hookah.rpc.api.PayBankCardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayBankCardImpl extends GenericServiceImpl<PayBankCard, String> implements PayBankCardService {

    @Resource
    private PayBankCardMapper payBankCardMapper;

    @Resource
    private IFixClient fixClient;

    @Resource
    public void setDao(PayBankCardMapper payBankCardMapper) {
        super.setDao(payBankCardMapper);
    }

    /**
     * @param userId
     * @param customerNum   客户号
     * @param bankName      银行名称
     * @param bankCardNum   银行卡号
     * @param bankCardOwner 持卡人
     * @param ip
     * @param ukey          k宝信息
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public boolean bankCardSignOn(String userId, String customerNum, String bankName, String bankCardNum, String bankCardOwner, String ip, String ukey) {
        //根据userId查询accountId
        // 组装参数
        Map<String, String> paramMap = buildSignOnParam(customerNum, bankCardNum, bankCardOwner, ip, ukey);
        PayBankCard payBankCard = new PayBankCard();
        //插入记录
//        payBankCard.setPayAccountId();
        payBankCard.setUserId(userId);
        payBankCard.setBankName(bankName);
        payBankCard.setBankCode(PayConstants.BankCode.NY02.code);
        payBankCard.setCardOwner(bankCardOwner);
        payBankCard.setCardCode(bankCardNum);
//        payBankCardMapper.insert(payBankCard);
        // 发起请求
        ResultBean<Map<String, String>> resultBean = this.fixClient.sendFirmSignOn(paramMap, new AxCallFunc() {
            public boolean onReply(JFixSess jFixSess, JFixComm jFixComm) {
                //回调函数处理
                return signOnReply(jFixSess, jFixComm, payBankCard);
            }
        });
        if (resultBean.isSuccess()) {
            //发送成功，插入mongo

        } else {
            //发送失败
            String errorMsg = "[fix error]" + String.format("[%s]%s", resultBean.getCode(), resultBean.getMsg());
            //todo 发送失败处理
            return false;
        }
        return true;
    }

    private boolean signOnReply(JFixSess jFixSess, JFixComm jFixComm, PayBankCard payBankCard) {
        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            /**
             * 异步调用返回：
             * FID_CODE 返回码
             * FID_MESSAGE   返回信息
             * FID_SQBH   申请编号
             * FID_CLJG   处理结果
             * FID_JGSM   结果说明
             * FID_MID     清算中心资金账户
             * FID_QYZT   签约状态
             */
            if (jFixSess.getCount() > 0) {
                jFixSess.go(0);
                if (jFixSess.getCode() > 0) {
                    String FID_SQBH = jFixSess.getItem(FixConstants.FID_SQBH);
                    String FID_CLJG = jFixSess.getItem(FixConstants.FID_CLJG);
                    String FID_JGSM = jFixSess.getItem(FixConstants.FID_JGSM);
                    String FID_MID = jFixSess.getItem(FixConstants.FID_MID);
                    String FID_QYZT = jFixSess.getItem(FixConstants.FID_QYZT);
                    String msg = jFixSess.getItem(FixConstants.FID_MESSAGE);
                    msg = msg != null ? msg : "处理成功";
                    //成功后处理
                    payBankCard.setBindFlag(PayConstants.BankCardStatus.binded.code);
                    payBankCardMapper.insert(payBankCard);
                    //TODO 将MID更新到pay_account表中
                } else {
                    String errorMsg = String.format("[fix async error][%s]%s", jFixSess.getItem(FixConstants.FID_CODE), jFixSess.getItem(FixConstants.FID_MESSAGE));
                    //todo 失败后处理
                }
            } else {
                String errorMsg = String.format("[fix async error][%s]%s", jFixSess.getItem(FixConstants.FID_CODE), jFixSess.getItem(FixConstants.FID_MESSAGE));
                //todo 失败后处理
            }

        } catch (Exception e) {
            e.printStackTrace();
            //todo 异常后处理
            return false;
        }
        return true;
    }


    @Override
    public boolean bankCardSignOff() {
        return false;
    }

    private Map<String, String> buildSignOnParam(String customerNum, String accountNum, String bankCardOwner, String ip, String ukey) {
        Map<String, String> paramMap = new HashMap<>();
        /**
         *  FID_JYS   交易市场*
         *  FID_BZ    币种*
         *  FID_SQRQ  申请日期
         *  FID_SQSJ  申请时间
         *  FID_SQH   交易市场申请号*
         *  FID_ZJZH  资金账户*
         *  FID_YHZH  银行账户*
         *  FID_YHDM  银行代码*
         *  FID_YHMM  银行密码
         *  FID_CZZD  操作站点
         *  FID_BZXX 备注信息
         *  FID_YHZHMC 银行账户名称*
         *  FID_WDH 网点号
         *  FID_ZJBH  证件编号
         *  FID_ZJLB 证件类别
         *  FID_CS1 预留参数1 如果需要签名信息，对应签名信息
         *  FID_CS2 预留参数2 签名流水号，如果是农行，此处必填，主要用来验证加签信息
         *  FID_CS3 预留参数3
         */

        // 获取交易市场申请号
        String serialNum = PayUtil.createChannelSerial(ChannelType.QDABC);

        paramMap.put("FID_JYS", PayConstants.FID_JYS);//交易市场
        paramMap.put("FID_BZ", PayConstants.QD_BZ);
        paramMap.put("FID_SQRQ", DateUtil.getCurrentDate("YYYYMMDD"));
        paramMap.put("FID_SQSJ", DateUtil.getCurrentTime("HH24:MI:SS"));
        paramMap.put("FID_SQH", serialNum);
        paramMap.put("FID_ZJZH", customerNum);//资金账号（客户号）
        paramMap.put("FID_YHZH", accountNum);//银行账户
        paramMap.put("FID_YHDM", PayConstants.BankCode.NY02.getCode());//银行代码
        paramMap.put("FID_YHMM", "");
        paramMap.put("FID_CZZD", ip);//操作站点
        paramMap.put("FID_BZXX", "");
        paramMap.put("FID_YHZHMC", bankCardOwner);//银行账户名称
        paramMap.put("FID_WDH", "");
        paramMap.put("FID_ZJBH", "");
        paramMap.put("FID_ZJLB", "");
        paramMap.put("FID_CS1", ukey);//k宝返回
        paramMap.put("FID_CS2", PayConstants.QDABC_PREFIX + ukey);//BD + k宝返回
        return paramMap;
    }
}
