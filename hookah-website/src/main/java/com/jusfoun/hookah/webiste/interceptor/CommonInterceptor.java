package com.jusfoun.hookah.webiste.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            if(!ajax){
                Map<String, Object> model = modelAndView.getModel();
                model.put("user", subject.getPrincipal());
            }

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
