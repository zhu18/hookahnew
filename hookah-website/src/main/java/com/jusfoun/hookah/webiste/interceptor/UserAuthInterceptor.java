package com.jusfoun.hookah.webiste.interceptor;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserDetail;
import com.jusfoun.hookah.rpc.api.UserService;
import com.jusfoun.hookah.webiste.util.PropertiesManager;
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

/**
 * Created by ctp on 2017/5/5.
 */
public class UserAuthInterceptor implements HandlerInterceptor {

    @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try{
            Subject subject = SecurityUtils.getSubject();
            if (subject != null && subject.isAuthenticated()) {
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
                userService = (UserService) factory.getBean("userService");

                Map userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
                User user = new User();
                BeanUtils.populate(user,userMap);
                user = userService.selectById(user.getUserId());

                Integer userType = user.getUserType();
                if(!HookahConstants.UserType.SYSTEM.getCode().equals(userType) &&
                        !HookahConstants.UserType.ORGANIZATION_CHECK_OK.getCode().equals(userType) &&
                        !HookahConstants.UserType.PERSON_CHECK_OK.getCode().equals(userType) &&
                        !HookahConstants.UserType.SUPPLIER_CHECK_NO.getCode().equals(userType) &&
                        !HookahConstants.UserType.SUPPLIER_CHECK_FAIL.getCode().equals(userType) &&
                        !HookahConstants.UserType.SUPPLIER_CHECK_OK.getCode().equals(userType)){
//                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/auth/index");
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PropertiesManager.getInstance().getProperty("no.auth.url"));
                    return false;
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        boolean ajax = "XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"));
//
//        if(!ajax && null != modelAndView){
//           try{
//               Subject subject = SecurityUtils.getSubject();
//               if (subject != null && subject.isAuthenticated()) {
//                   BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
//                   userService = (UserService) factory.getBean("userService");
//
//                   Map userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
//                   User user = new User();
//                   BeanUtils.populate(user,userMap);
//                   user = userService.selectById(user.getUserId());
//
//                   Integer userType = user.getUserType();
//                   if(!HookahConstants.UserType.ORGANIZATION_CHECK_OK.equals(userType) && !HookahConstants.UserType.PERSON_CHECK_OK.equals(userType)){
//                      modelAndView.setViewName("/auth/index");
//                      return;
//                   }
//
//               }
//           }catch (Exception e) {
//                e.printStackTrace();
//           }
//        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
