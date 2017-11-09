package com.jusfoun.hookah.crowd.interceptor;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.crowd.constants.ZbContants;
import com.jusfoun.hookah.crowd.service.MgZbProviderService;
import com.jusfoun.hookah.crowd.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ctp on 2017/5/5.
 * 服务商是否认证
 */
public class UserAuthInterceptor implements HandlerInterceptor {

    @Resource
    UserService userService;

    @Resource
    MgZbProviderService mgZbProviderService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try{
            Subject subject = SecurityUtils.getSubject();
            if (subject != null && subject.isAuthenticated()) {
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
                mgZbProviderService = (MgZbProviderService) factory.getBean("mgZbProviderService");

                Map userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
                User user = new User();
                BeanUtils.populate(user,userMap);
                MgZbProvider mgZbProvider = mgZbProviderService.selectById(user.getUserId());
                if(Objects.nonNull(mgZbProvider)){
                   Integer authType = mgZbProvider.getAuthType();
                   Integer authStatus = mgZbProvider.getStatus();
                    if(ZbContants.Provider_Auth_Status.AUTH_SUCCESS.getCode().equals(authStatus)){
                        if(null != authType && Integer.valueOf(4).equals(authType)){//服务商企业认证
                            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/usercenter/epAuthentication");
                            return false;
                        } else if(null != authType && Integer.valueOf(2).equals(authType)){//服务商个人认证
                            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/usercenter/pspAuthentication");
                            return false;
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
