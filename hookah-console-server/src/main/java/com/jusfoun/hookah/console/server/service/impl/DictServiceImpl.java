package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.DictMapper;
import com.jusfoun.hookah.core.domain.Dict;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/4/13 下午2:10
 * @desc
 */
@Service
public class DictServiceImpl extends GenericServiceImpl<Dict, Long> implements DictService {

    @Resource
    private DictMapper dictMapper;

    @Resource
    public void setDao(DictMapper dictMapper) {
        super.setDao(dictMapper);
    }


}
