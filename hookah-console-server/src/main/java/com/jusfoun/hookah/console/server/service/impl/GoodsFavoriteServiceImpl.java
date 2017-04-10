package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.GoodsFavoriteMapper;
import com.jusfoun.hookah.core.dao.RegionMapper;
import com.jusfoun.hookah.core.domain.GoodsFavorite;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.GoodsFavoriteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by admin on 2017/4/5/0005.
 */
@Service
public class GoodsFavoriteServiceImpl extends GenericServiceImpl<GoodsFavorite, String> implements GoodsFavoriteService {

    @Resource
    private GoodsFavoriteMapper goodsFavoriteMapper;

    @Resource
    public void setDao(GoodsFavoriteMapper goodsFavoriteMapper) {
        super.setDao(goodsFavoriteMapper);
    }

}
