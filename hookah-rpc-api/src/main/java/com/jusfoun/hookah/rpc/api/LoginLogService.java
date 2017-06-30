package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.LoginLog;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * dx
 */
public interface LoginLogService extends GenericService<LoginLog, String> {

    void addLoginLog(String username, String ipAddr);
}
