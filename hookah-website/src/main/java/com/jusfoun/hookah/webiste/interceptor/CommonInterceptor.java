package com.jusfoun.hookah.webiste.interceptor;

import com.jusfoun.hookah.core.domain.Help;
import com.jusfoun.hookah.rpc.api.HelpService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null && subject.isAuthenticated()) {
                if (!ajax) {
                    Map<String, Object> model = modelAndView.getModel();
                    model.put("user", subject.getPrincipal());
                }

            }
        } catch (UnavailableSecurityManagerException e) {

        }
        if (!ajax && modelAndView != null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
            HelpService helpService = (HelpService) factory.getBean("helpService");
            List<Help> helpList = helpService.selectList();
            Map<String, Object> model = modelAndView.getModel();
            model.put("help", helpList);
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
