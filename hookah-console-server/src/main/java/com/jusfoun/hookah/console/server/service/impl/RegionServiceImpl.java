package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.RegionMapper;
import com.jusfoun.hookah.core.domain.Region;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.RegionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by admin on 2017/4/5/0005.
 */
@Service
public class RegionServiceImpl extends GenericServiceImpl<Region, Long> implements RegionService {

    @Resource
    private RegionMapper regionMapper;

    @Resource
    public void setDao(RegionMapper regionMapper) {
        super.setDao(regionMapper);
    }

    /**
     * redis序列化的时候Long类型无法被转换，暂未找到解决方案
     * @param id
     * @return
     */
    @Cacheable(value = "regionInfo")
    @Override
    public Region selectById(String id) {
        return regionMapper.selectByPrimaryKey(Long.valueOf(id));
    }

}
