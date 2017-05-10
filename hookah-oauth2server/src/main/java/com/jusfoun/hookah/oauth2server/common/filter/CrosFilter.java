package com.jusfoun.hookah.oauth2server.common.filter;


import com.jusfoun.hookah.oauth2server.config.MyProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author huang lei
 * @date 2016/12/25 下午5:13
 * @desc
 */
@Component()
@Order(1)
public class CrosFilter implements Filter {

    @Autowired
    private MyProps myProps;

    String domain;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        domain = myProps.getHost().get("domain");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        String origin = httpServletRequest.getHeader("Origin");
//        if (StringUtils.isNoneBlank(origin)) {
//            Pattern pattern = Pattern.compile("([a-zA-z]+://){0,1}([^\\s]*)");
//            Matcher matcher = pattern.matcher(origin);
//            if (matcher.find()) {
//                String host = matcher.group(2);
//                if (host.endsWith(domain) && !host.equals(domain) && StringUtils.isNoneBlank(getDomain(host))) {
//                    httpServletResponse.addHeader("Access-Control-Allow-Origin", httpServletRequest.getScheme() + "://" + getDomain(host) + "." + domain);
//                    httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,HEAD,OPTIONS,PATCH,PUT");
//                    httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
//                    httpServletResponse.setHeader("Access-Control-Allow-Headers", "X-Requested-With,X-Auth-Token,content-type");
//                    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//                }
//            }
//        }


        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private String getDomain(String host) {
        if (host.endsWith(domain)) {
            return host.substring(0, host.length() - (domain.length() + 1));
        } else return null;
    }
}
