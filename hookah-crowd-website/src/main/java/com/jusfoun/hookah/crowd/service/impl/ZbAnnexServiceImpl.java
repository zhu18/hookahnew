package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbAnnexMapper;
import com.jusfoun.hookah.core.domain.zb.ZbAnnex;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.ZbAnnexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by computer on 2017/9/20.
 */
@Service
public class ZbAnnexServiceImpl extends GenericServiceImpl<ZbAnnex, Long> implements ZbAnnexService {

    @Resource
    ZbAnnexMapper zbAnnexMapper;

    @Resource
    public void setDao(ZbAnnexMapper zbAnnexMapper) {
        super.setDao(zbAnnexMapper);
    }
}
