package com.jusfoun.hookah.webiste.filter;

import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huang lei
 * @date 2017/3/30 上午9:23
 * @desc
 */
public class SystemLogoutFilter extends LogoutFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String uri = httpServletRequest.getRequestURI();
        String regExp = "^/logout.*";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(uri);
        if(m.matches()){
//            OAuthClientRequest oAuthClientRequest = OAuthClientRequest.authorizationLocation("http://localhost:9900/logout").buildQueryMessage();
//            URLConnectionClient httpClient = new URLConnectionClient();
//            httpClient.execute(oAuthClientRequest, null, "GET", null);
            subject.logout();
            issueRedirect(request, response, redirectUrl);
            return false;
        }else{
            return true;
        }

    }


}
