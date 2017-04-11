package com.jusfoun.hookah.webiste.interceptor;

import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.rpc.api.CartService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 购物车全局变量
 * @author: jsshao
 * @date:2016/12/19 12:09
 */
public class CartInterceptor implements HandlerInterceptor {
    //无法正常注入
    @Resource
    CartService cartService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        boolean ajax = "XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"));
        Map<String, Object> model = null;
        if(!ajax && modelAndView!=null){
            model = modelAndView.getModel();
            try {
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
                cartService = (CartService) factory.getBean("cartService");

                Subject subject = SecurityUtils.getSubject();

                if (subject != null && subject.isAuthenticated()) {
                    List<Condition> filters = new ArrayList<>();
                    filters.add(Condition.eq("userId", subject.getPrincipal()));
                    List<CartVo> cartVos = cartService.selectDetailList(filters,null);

                    model.put("cartList", cartVos);
                    model.put("cartSize", cartVos.size());
                }
            } catch (UnavailableSecurityManagerException e) {
                //e.printStackTrace();
                System.out.println(e.getMessage());
                model.put("cartList", new ArrayList(0));
                model.put("cartSize", 0);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
