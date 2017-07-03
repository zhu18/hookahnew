package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.dao.LoginLogMapper;
import com.jusfoun.hookah.core.domain.LoginLog;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.LoginLogService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * dx
 */
@Service
public class LoginLogServiceImpl extends GenericServiceImpl<LoginLog, String> implements LoginLogService {

    private static final Logger logger = LoggerFactory.getLogger(LoginLogServiceImpl.class);

    @Resource
    LoginLogMapper loginLogMapper;

    @Resource
    public void setDao(LoginLogMapper loginLogMapper) {
        super.setDao(loginLogMapper);
    }


    @Resource
    UserService userService;

    @Async
    public void addLoginLog(String username, String ipAddr) {
        User updateUser = new User();
        updateUser.setLastLoginIp(ipAddr);
        updateUser.setLastLoginTime(new Date());
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("userName", username));
        userService.updateByConditionSelective(updateUser,filters);

        LoginLog loginLog = new LoginLog();
        loginLog.setLoginName(username);
        loginLog.setClientIp(ipAddr);
        loginLog.setCreateUserId("SYSTEM");
        loginLog.setAddTime(new Date());
        loginLog.setIsDelete(Byte.parseByte("1"));
        int n = loginLogMapper.insert(loginLog);
        logger.info("用户[" + username + "]登录日志写入-{}", n == 1 ? "成功" : "失败");
    }
}
