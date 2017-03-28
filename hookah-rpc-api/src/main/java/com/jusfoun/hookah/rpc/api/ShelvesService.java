package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.GoodsShelves;
import com.jusfoun.hookah.core.domain.vo.OptionalShelves;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * @author huang lei
 * @date 2017/2/28 下午3:06
 * @desc
 */
public interface ShelvesService extends GenericService<GoodsShelves,String> {
    OptionalShelves getOptionShelves();
}
