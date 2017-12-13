package com.jusfoun.hookah.webiste.interceptor;


import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgTongJi;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.MgTongJiService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class TongJiInterceptor implements HandlerInterceptor {

    protected final static Logger logger = LoggerFactory.getLogger(TongJiInterceptor.class);

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        String utmSource = request.getParameter("utm_source");
        String utmTerm = request.getParameter("utm_term");
        saveCookie(request, response, utmSource, utmTerm);
        return true;
    }


    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

    //保存统计信息
    public void saveCookie(HttpServletRequest request, HttpServletResponse response, String utmSource, String utmTerm) {
        try {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            MgTongJiService mgTongJiService = (MgTongJiService) factory.getBean("mgTongJiService");
            Subject subject = SecurityUtils.getSubject();
            //获取userId
            String userId = null;
            if (subject != null && subject.isAuthenticated()) {
                Map userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
                User user = new User();
                BeanUtils.populate(user,userMap);
                userId = user.getUserId();
            }
            userId = userId == null ? "无" : userId;
            //获取cookie中TongJi
            Map<String, Cookie> cookieMap = ReadCookieMap(request);
            Cookie tongJi = cookieMap.get("TongJi");
            String utmSource1 = utmSource;
            String utmTerm1 = utmTerm;
            //获取当前链接url
            String uri = request.getRequestURI();
            if(uri.contains(".css") || uri.contains(".js") || uri.contains(".png")||
                    uri.contains(".jpg") || uri.contains(".html") || uri.contains("api/image/list")){
                return;
            }else {
                if (tongJi == null) {
                    String uuid = StringUtils.getUUID();
                    Cookie cookie = new Cookie("TongJi", uuid);
                    cookie.setDomain("bdgstore.cn");
                    response.addCookie(cookie);
                    mgTongJiService.setTongJiInfo("www.bdgstore.cn" + uri, uuid,
                            utmSource1 == null ? "直接访问" : utmSource1, utmTerm1 == null ? "无" : utmTerm1, userId);
                }else {
                    //获取最近登录的信息来源
                    if(utmSource == null || utmTerm == null){
                        MgTongJi tongJiInfo = mgTongJiService.getTongJiInfo(tongJi.getValue());
                        if(tongJiInfo != null){
                            utmSource1 = tongJiInfo.getUtmSource();
                            utmTerm1 = tongJiInfo.getUtmTerm();
                        }
                    }
                    mgTongJiService.setTongJiInfo("www.bdgstore.cn" + uri, tongJi.getValue(),
                            utmSource1 == null ? "直接访问" : utmSource1, utmTerm1 == null ? "无" : utmTerm1, userId);
                }
            }
        }catch (Exception e){
            //e.printStackTrace();
            logger.info("未获取统计信息");
        }
    }

    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
