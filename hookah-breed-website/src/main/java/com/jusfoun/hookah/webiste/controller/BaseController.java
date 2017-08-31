package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.JsonUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller 基类
 *
 * @author:jsshao
 * @date: 2017-3-31
 */
public abstract class BaseController {
    /**
     * 错误视图
     */
    protected static final String ERROR_VIEW = "/exception/error";

    /**
     * 当前页码
     */
    protected static final String PAGE_NUM = "1";

    /**
     * 每页记录数
     */
    protected static final String PAGE_SIZE = "20";

    protected Logger logger = LoggerFactory.getLogger(getClass().getName());

    protected User getCurrentUser() throws HookahException{
        Map userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
        if(userMap==null){
            throw  new HookahException("没有登录用户信息");
        }
        User user = new User();
        try {
            BeanUtils.populate(user,userMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new HookahException("获取用户信息出错！",e);
        }
        logger.info(JsonUtils.toJson(user));
        return user;
    }
}
