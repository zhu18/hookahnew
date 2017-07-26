package com.jusfoun.hookah.pay.service.impl;

/**
 * Created by ndf on 2017/7/5.
 */

//import com.apex.etm.qss.client.IFixClient;
//import com.apex.etm.qss.client.fixservice.FixConstants;
//import com.apex.etm.qss.client.fixservice.bean.ResultBean;
//import com.apex.fix.AxCallFunc;
//import com.apex.fix.JFixComm;
//import com.apex.fix.JFixSess;

import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.FixConstants;
import com.apex.etm.qss.client.fixservice.bean.ResultBean;
import com.apex.fix.AxCallFunc;
import com.apex.fix.JFixComm;
import com.apex.fix.JFixSess;
import com.jusfoun.hookah.core.dao.SyncCustomerInfoMapper;
import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.pay.util.SyncCustomerInfoFlag;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.SyncCustomerInfoService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SyncCustomerInfoImpl extends GenericServiceImpl<PayAccount, Integer> implements SyncCustomerInfoService {

    @Autowired
    private FixClientUtil client;

    private IFixClient fixClient=client.createClientSSL();

    @Resource
    PayAccountService payAccountService;

    @Resource
    OrganizationService organizationService;

    @Resource
    UserService userService;

    @Resource
    private SyncCustomerInfoMapper syncCustomerInfoMapper;

    @Resource
    public void setDao(SyncCustomerInfoMapper syncCustomerInfoMapper) {
        super.setDao(syncCustomerInfoMapper);
    }
    /**
     * 客户基本信息同步(719001)
     * @return
     */
    public void sendFirmReg(String userId){

        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("userId", userId));
        //根据userId查询虚拟账户表
        PayAccount payAccount = payAccountService.selectOne(filters);
        //根据userId查询用户
        User user = userService.selectById(userId);
        //根据用户表orgId查询机构
        Organization organization = organizationService.selectById(user.getOrgId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String addTime = simpleDateFormat.format(payAccount.getAddTime());
        Map<String, String> paramMap = new HashMap<String, String>();
        /**
         * FID_JYS 交易市场*
         * FID_HYDM 会员代码
         * FID_KHH 市场端客户号*
         * FID_KHXM 客户姓名
         * FID_KHQC 客户全称*
         * FID_ZJBH 证件编号*
         * FID_JGBZ 机构标志*
         * FID_ZJZH 市场端资金账号*
         * FID_JYZH 市场端交易账号*
         * FID_FRDB 法定代表人
         * FID_ZJBH_FRDB 法定代表人证件编号
         * FID_SHXYDMZ 社会统一信用代码证
         * FID_ZJLB 证件类别*
         * FID_TZZLX 投资者类型*
         * FID_KHZT 客户状态*
         * FID_BZ 币种* 默认RMB
         * FID_FRDB 法定代表人
         * FID_ZJBH_FRDB 法定代表人证件编号
         * FID_ZJLB_FRDB 法人代表证件类别(JGBZ=0时可以为空)
         * FID_ZHLB 账户类别 默认0
         * FID_WDH  对应农行的请求流水号
         */
        paramMap.put("FID_JYS", PayConstants.FID_JYS);
        paramMap.put("FID_FZJGDM",Integer.toString(0));
        paramMap.put("FID_KHH", payAccount.getUserId());
        paramMap.put("FID_KHQC",organization.getOrgName());//客户全称
        paramMap.put("FID_ZJLB",Integer.toString(8));
        paramMap.put("FID_ZJBH",organization.getLicenseCode());//证件编号
        paramMap.put("FID_JGBZ",Integer.toString(1));
        paramMap.put("FID_GJDM",Integer.toString(156));//国籍
        paramMap.put("FID_ZHLB",Integer.toString(9));
        paramMap.put("FID_TZZLX",Integer.toString(0));//投资者类型
        paramMap.put("FID_KHZT",Integer.toString(payAccount.getAccountFlag()));//客户状态
        paramMap.put("FID_KHRQ",addTime);//开户时间
        paramMap.put("FID_XHRQ"," ");
        paramMap.put("FID_ZJZH",payAccount.getUserId());
        paramMap.put("FID_BZ","RMB");
        //机构必填
        if(user.getUserType() == 4) {
            paramMap.put("FID_ZJLB_FRDB", Integer.toString(0));//机构法人证
            paramMap.put("FID_ZJBH_FRDB", "");//机构法人证件编号-------
            paramMap.put("FID_FRDB", organization.getLawPersonName());//法人代表
            paramMap.put("FID_ZCDZ", organization.getRegion());//注册地址
            paramMap.put("FID_BGDZ", organization.getContactAddress());//办公地址
            paramMap.put("FID_SHDMZ", "");//统一社会信用代码-----
        }
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
                            String FID_CODE =  jFixSess.getItem(FixConstants.FID_CODE);
                            msg = msg != null ? msg : "处理成功";

                            //todo 成功后处理
                            if(Integer.parseInt(FID_CODE) >= 0) { // >=0 成功 <0 失败
                                //成功后清算所账户资金加入账户表
                                payAccount.setMerchantId(FID_MID);
                            }
                            payAccount.setUpdateOperator("SYSTEM");
                            payAccount.setUpdateTime(new Date());
                            payAccount.setSyncFlag(SyncCustomerInfoFlag.SYNCHRONIZED);
                        }else{
                            String errorMsg = String.format("[fix async error][%s]%s",jFixSess.getItem(FixConstants.FID_CODE), jFixSess.getItem(FixConstants.FID_MESSAGE));
                            //todo 失败后处理
                            payAccount.setUpdateOperator("SYSTEM");
                            payAccount.setUpdateTime(new Date());
                            payAccount.setSyncFlag(SyncCustomerInfoFlag.NOT_SYNCHRONIZED);
                        }
                    }else{
                        String errorMsg = String.format("[fix async error][%s]%s",jFixSess.getItem(FixConstants.FID_CODE), jFixSess.getItem(FixConstants.FID_MESSAGE));
                        //todo 失败后处理
                        payAccount.setUpdateTime(new Date());
                        payAccount.setUpdateOperator("SYSTEM");
                        payAccount.setSyncFlag(SyncCustomerInfoFlag.NOT_SYNCHRONIZED);
                    }
                    int i = payAccountService.updateById(payAccount);
                    logger.info("业务处理--->payAccount修改" + (i > 0 ? "客户信息同步完成" : "客户信息同步未完成") + "-->操作时间：" + LocalDateTime.now());

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
            payAccount.setUpdateTime(new Date());
            payAccount.setUpdateOperator("SYSTEM");
            payAccount.setSyncFlag(SyncCustomerInfoFlag.SYNCHRONIZATION);
            payAccountService.updateById(payAccount);
            logger.info("客户信息同步完成中  ---->操作时间：" + LocalDateTime.now());

        } else{
            //发送失败
            String errorMsg = "[fix error]" + String.format("[%s]%s",resultBean.getCode() , resultBean.getMsg());
            //todo 发送失败处理
            payAccount.setUpdateTime(new Date());
            payAccount.setUpdateOperator("SYSTEM");
            payAccount.setSyncFlag(SyncCustomerInfoFlag.NOT_SYNCHRONIZED);
            payAccountService.updateById(payAccount);
            logger.info("客户信息同步未完成  ---->操作时间：" + LocalDateTime.now());
        }
    }
}
