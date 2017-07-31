package com.jusfoun.hookah.pay.service.impl;

import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.bean.ResultBean;
import com.jusfoun.hookah.pay.util.DateUtil;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.rpc.api.DailyFundFileUploadedNoticeService;
import com.jusfoun.hookah.rpc.api.MgDailyFileCheckService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 上传、发送 清算文件前的校验 719021
 * FID_WJLX 参数 先做11 or 22 的检查 判断文件是否允许上传
 * 文件上传成功后在做1 or 2 的确认
 */
@Service
public class DailyFundFileUploadedNoticeImpl implements DailyFundFileUploadedNoticeService {

//    @Resource
//    FixClientUtil client;

    private IFixClient fixClient = FixClientUtil.createClientSSL();

    @Resource
    MgDailyFileCheckService mgDailyFileCheckService;

    /**
     * @param noticeType 清算类型
     *                  /**
     * FID_YWRQ 业务日期
     * FID_JYS 交易市场
     * FID_WJLX 文件类型 （1资金日终    11撤销（检查）资金日终    2交易日终  22撤销（检查）交易日终）
     *          先做11 or 22 的检查，处理成功后在做1 or 2 的确认
     */
    public boolean dailyFundFileUploadedNotice(String noticeType) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("FID_YWRQ", DateUtil.dateCurrentForYMD());//业务日期
        paramMap.put("FID_JYS", PayConstants.FID_JYS);//交易市场
        paramMap.put("FID_WJLX", noticeType);//清算类型
        ResultBean<Map<String, String>> resultBean = this.fixClient.sendFundFileCheck(paramMap);
        boolean bool = resultBean.isSuccess();
        return bool;
    }
}