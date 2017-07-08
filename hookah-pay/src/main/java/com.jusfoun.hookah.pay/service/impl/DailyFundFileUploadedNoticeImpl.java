package com.jusfoun.hookah.pay.service.impl;

import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.bean.ResultBean;
import com.jusfoun.hookah.pay.util.DateUtil;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.rpc.api.DailyFundFileUploadedNoticeService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class DailyFundFileUploadedNoticeImpl implements DailyFundFileUploadedNoticeService {
    @Resource
    private MongoTemplate mongoTemplate;


    @Resource
    FixClientUtil client;

    private IFixClient fixClient = client.createClientSSL();

    /**
     * @param fileType 清算类型
     * @return
     */
    public boolean dailyFundFileUploadedNotice(String fileType) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("FID_YWRQ", DateUtil.getCurrentTime("YYYYMMDD"));//业务日期
        paramMap.put("FID_JYS", PayConstants.FID_JYS);//交易市场
        paramMap.put("FID_WJLX",fileType);//清算类型
        ResultBean<Map<String, String>> resultBean = this.fixClient.sendFundFileCheck(paramMap);
        return resultBean.isSuccess();
    }
}