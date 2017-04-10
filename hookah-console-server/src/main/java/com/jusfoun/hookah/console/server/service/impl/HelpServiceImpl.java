package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.HelpMapper;
import com.jusfoun.hookah.core.domain.Help;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.HelpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/4/10 下午4:30
 * @desc
 */
@Service
public class HelpServiceImpl extends GenericServiceImpl<Help, String> implements HelpService {

    @Resource
    HelpMapper helpMapper;

    @Resource
    public void setDao(HelpMapper helpMapper) {
        super.setDao(helpMapper);
    }

}
