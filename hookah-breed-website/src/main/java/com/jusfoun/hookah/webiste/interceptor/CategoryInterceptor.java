package com.jusfoun.hookah.webiste.interceptor;

import com.jusfoun.hookah.rpc.api.CategoryService;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: 商品分类树 全局变量
 * @author: jsshao
 * @date:2016/12/19 12:09
 */
public class CategoryInterceptor implements HandlerInterceptor {
    //无法正常注入
    @Resource
    CategoryService categoryService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        boolean ajax = "XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"));
        try {
            if(!ajax){
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
                categoryService = (CategoryService) factory.getBean("categoryService");

                Map<String, Object> model = modelAndView.getModel();
                model.put("categoryInfo", categoryService.getCatTree());
            }
        } catch (UnavailableSecurityManagerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
