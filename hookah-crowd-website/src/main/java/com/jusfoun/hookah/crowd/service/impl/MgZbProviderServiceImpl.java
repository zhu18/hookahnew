package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.MgZbProviderService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MgZbProviderServiceImpl extends GenericMongoServiceImpl<MgZbProvider, String> implements MgZbProviderService {

    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public ReturnData userAuth(MgZbProvider provider) {
        return null;
    }
}
