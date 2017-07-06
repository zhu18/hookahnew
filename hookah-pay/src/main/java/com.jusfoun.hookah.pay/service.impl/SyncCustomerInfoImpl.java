package com.jusfoun.hookah.pay.service.impl;

/**
 * Created by ndf on 2017/7/5.
 */

import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.FixConstants;
import com.apex.etm.qss.client.fixservice.bean.ResultBean;
import com.apex.fix.AxCallFunc;
import com.apex.fix.JFixComm;
import com.apex.fix.JFixSess;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.pay.util.PayConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class SyncCustomerInfoImpl {

    @Resource
    private FixClientUtil client;

    private IFixClient fixClient=client.createClient();

    /**
     * 客户基本信息同步(719001)
     */
    public void sendFirmReg(){
        Map<String, String> paramMap = new HashMap<String, String>();
        /**
         * FID_JYS 交易市场*
         *              FID_HYDM 会员代码
         *              FID_KHH 市场端客户号*
         *              FID_KHXM 客户姓名
         *              FID_KHQC 客户全称*
         *              FID_ZJBH 证件编号*
         *              FID_JGBZ 机构标志*
         *              FID_ZJZH 市场端资金账号*
         *              FID_JYZH 市场端交易账号*
         *              FID_FRDB 法定代表人
         *              FID_ZJBH_FRDB 法定代表人证件编号
         *              FID_SHXYDMZ 社会统一信用代码证
         *              FID_ZJLB 证件类别*
         *              FID_TZZLX 投资者类型*
         *              FID_KHZT 客户状态*
         *              FID_BZ 币种* 默认RMB
         *             FID_FRDB 法定代表人
         *              FID_ZJBH_FRDB 法定代表人证件编号
         *              FID_ZJLB_FRDB 法人代表证件类别(JGBZ=0时可以为空)
         *              FID_ZHLB 账户类别 默认0
         *              FID_WDH  对应农行的请求流水号
         */
        paramMap.put("FID_JYS", PayConstants.FID_JYS);
        //todo 示例代码参数省略...

        AxCallFunc callFunc = new AxCallFunc() {
            public boolean onReply(JFixSess jFixSess, JFixComm jFixComm) {
                try{
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    /**
                     * 异步调用返回：
                     *                  FID_CODE
                     *      FID_MESSAGE
                     *      FID_CID  清算中心客户号
                     *      FID_MID  清算中心资金账户
                     */
                    if(jFixSess.getCount() > 0){
                        jFixSess.go(0);
                        if (jFixSess.getCode() > 0){
                            String FID_CID =  jFixSess.getItem(FixConstants.FID_CID);
                            String FID_MID =  jFixSess.getItem(FixConstants.FID_MID);
                            String msg =  jFixSess.getItem(FixConstants.FID_MESSAGE);
                            msg = msg != null ? msg : "处理成功";

                            //todo 成功后处理

                        }else{
                            String errorMsg = String.format("[fix async error][%s]%s",jFixSess.getItem(FixConstants.FID_CODE), jFixSess.getItem(FixConstants.FID_MESSAGE));
                            //todo 失败后处理
                        }
                    }else{
                        String errorMsg = String.format("[fix async error][%s]%s",jFixSess.getItem(FixConstants.FID_CODE), jFixSess.getItem(FixConstants.FID_MESSAGE));
                        //todo 失败后处理
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    //todo 异常后处理
                    return false;
                }
                return true;
            }
        };

        ResultBean<Map<String, String>> resultBean = this.fixClient.sendFirmReg(paramMap, callFunc);
        if(resultBean.isSuccess()){
            //发送成功
            //todo 发送成功处理
        } else{
            //发送失败
            String errorMsg = "[fix error]" + String.format("[%s]%s",resultBean.getCode() , resultBean.getMsg());
            //todo 发送失败处理
        }
    }
}
