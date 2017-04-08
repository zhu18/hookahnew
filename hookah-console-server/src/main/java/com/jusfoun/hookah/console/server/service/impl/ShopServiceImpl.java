package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.ShopMapper;
import com.jusfoun.hookah.core.domain.Shop;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.ShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/4/8 上午11:36
 * @desc
 */
@Service
public class ShopServiceImpl extends GenericServiceImpl<Shop, String> implements ShopService {

    @Resource
    ShopMapper shopMapper;

    @Resource
    public void setDao(ShopMapper shopMapper) {
        super.setDao(shopMapper);
    }

}
