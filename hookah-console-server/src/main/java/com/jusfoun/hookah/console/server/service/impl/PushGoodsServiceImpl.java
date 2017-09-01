package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.PushGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by admin on 2017/8/30.
 */
@Service
public class PushGoodsServiceImpl extends GenericServiceImpl<Goods, String> implements PushGoodsService {
    @Resource
    private GoodsMapper goodsMapper;


}
