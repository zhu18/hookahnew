package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Dict;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/13 下午2:09
 * @desc
 */
public interface DictService extends GenericService<Dict, Long> {

    List<Dict> selectTree();
}
