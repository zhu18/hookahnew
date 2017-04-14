package com.jusfoun.hookah.staticserver.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huang lei
 * @date 2016/12/25 下午5:13
 * @desc
 */
@Component()
@Order(1)
@ConfigurationProperties(prefix = "myconf")
public class CrosFilter implements Filter {

    private Map<String, String> host = new HashMap<>();

    final String domain = host.get("domain");

    public Map<String, String> getHost() {
        return host;
    }

    public void setHost(Map<String, String> host) {
        this.host = host;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String origin = httpServletRequest.getHeader("Origin");
        if (StringUtils.isNoneBlank(origin)) {
            Pattern pattern = Pattern.compile("([a-zA-z]+://){0,1}([^\\s]*)");
            Matcher matcher = pattern.matcher(origin);
            if (matcher.find()) {
                String host = matcher.group(2);
                if (host.endsWith(domain) && !host.equals(domain) && StringUtils.isNoneBlank(getDomain(host))) {
                    httpServletResponse.addHeader("Access-Control-Allow-Origin", httpServletRequest.getScheme() + "://" + getDomain(host) + "." + domain);
                    httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,HEAD,OPTIONS,PATCH,PUT");
                    httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
                    httpServletResponse.setHeader("Access-Control-Allow-Headers", "X-Requested-With,X-Auth-Token,content-type");
                    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
                }
            }
        }


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
