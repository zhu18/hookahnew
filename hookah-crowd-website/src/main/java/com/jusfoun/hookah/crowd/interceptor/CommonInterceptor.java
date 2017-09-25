package com.jusfoun.hookah.crowd.interceptor;

import com.jusfoun.hookah.core.domain.Help;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.crowd.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description:
 * @author: huanglei
 * @date:2016/12/19 12:09
 */
public class CommonInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        boolean ajax = "XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"));
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
        try {
            if(Objects.nonNull(modelAndView)){
                Subject subject = SecurityUtils.getSubject();
                if (subject != null && subject.isAuthenticated()) {
                    if (!ajax) {
                        Session session = subject.getSession();
                        Map userMap = (Map)session.getAttribute("user");
                        String userId = (String)userMap.get("userId");
                        UserService userService = (UserService) factory.getBean("userService");
                        User user = userService.selectById(userId);
                        Map<String, Object> model = modelAndView.getModel();
                        model.put("user", user);
                    }
                }
            }
        } catch (UnavailableSecurityManagerException e) {

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
