package com.jusfoun.hookah.pay.service.impl;

import com.apex.etm.qss.client.IFixClient;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.rpc.api.DailyFundFileUploadedNoticeService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DailyFundFileUploadedNoticeImpl implements DailyFundFileUploadedNoticeService {
    @Resource
    private MongoTemplate mongoTemplate;


    @Resource
    FixClientUtil client;

    private IFixClient fixClient = client.createClientSSL();


    public boolean dailyFundFileUploadedNotice(){

        return true;
    }
}