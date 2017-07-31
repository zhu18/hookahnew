package com.jusfoun.hookah.pay.service.impl;

import com.jusfoun.hookah.core.domain.mongo.MgDailyFileCheck;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.MgDailyFileCheckService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MgDailyFileCheckServiceImpl extends GenericMongoServiceImpl<MgDailyFileCheck, String> implements MgDailyFileCheckService {

    @Resource
    private MongoTemplate mongoTemplate;

}
