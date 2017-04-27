package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Region;
import com.jusfoun.hookah.core.generic.GenericService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by admin on 2017/4/5/0005.
 */
public interface RegionService extends GenericService<Region,Long> {

    @Cacheable(value = "regionInfo")
    Region selectById(String id);

    List<Region> selectTree();
}
