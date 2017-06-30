package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.dao.CashRecordMapper;
import com.jusfoun.hookah.core.dao.LoginLogMapper;
import com.jusfoun.hookah.core.domain.LoginLog;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.LoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * dx
 */
@Service
public class LoginLogServiceImpl extends GenericServiceImpl<LoginLog, String> implements LoginLogService {

    @Resource
    LoginLogMapper loginLogMapper;

    @Resource
    public void setDao(LoginLogMapper loginLogMapper) {
        super.setDao(loginLogMapper);
    }



}
