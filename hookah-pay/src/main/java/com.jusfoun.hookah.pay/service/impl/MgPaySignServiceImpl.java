package com.jusfoun.hookah.pay.service.impl;

import com.jusfoun.hookah.core.domain.mongo.MgPaySign;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.MgPaySignService;
import org.springframework.stereotype.Service;

/**
 * Created by hhh on 2017/7/7.
 */
@Service
public class MgPaySignServiceImpl extends GenericMongoServiceImpl<MgPaySign, String> implements MgPaySignService {
}
