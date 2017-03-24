package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.GoodsShelvesMapper;
import com.jusfoun.hookah.core.domain.GoodsShelves;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.ShelvesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class ShelvesServiceImpl extends GenericServiceImpl<GoodsShelves, String> implements ShelvesService {
    @Resource
    private GoodsShelvesMapper goodsShelvesMapper;

    @Resource
    public void setDao(GoodsShelvesMapper goodsShelvesMapper) {
        super.setDao(goodsShelvesMapper);
    }
}
