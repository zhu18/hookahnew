package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.domain.mongo.MgGoodsOrder;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.MgGoodsOrderService;
import org.springframework.stereotype.Service;

/**
 * Created by lt on 2017/7/18.
 */
@Service
public class MgGoodsOrderServiceImpl extends GenericMongoServiceImpl<MgGoodsOrder, String> implements MgGoodsOrderService {
}
