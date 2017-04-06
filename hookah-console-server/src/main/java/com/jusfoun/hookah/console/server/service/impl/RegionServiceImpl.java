package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.RegionMapper;
import com.jusfoun.hookah.core.domain.Region;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.RegionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by admin on 2017/4/5/0005.
 */
@Service
public class RegionServiceImpl extends GenericServiceImpl<Region, String> implements RegionService {

    @Resource
    private RegionMapper regionMapper;

    @Resource
    public void setDao(RegionMapper regionMapper) {
        super.setDao(regionMapper);
    }

    @Override
    public List<Region> getProvinces() {
        List<Region> list = regionMapper.selectAllProvince();
        return list;

    }

    @Override
    public List<Region> getCitys(Long parentId) {
        return regionMapper.selectAllCitys(parentId);
    }

}
