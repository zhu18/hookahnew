package com.jusfoun.hookah.console.other.service.impl;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.other.GoodsMongoService;
import org.springframework.stereotype.Service;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class GoodsMongoServiceImpl extends GenericMongoServiceImpl<Goods, String> implements GoodsMongoService {

}
