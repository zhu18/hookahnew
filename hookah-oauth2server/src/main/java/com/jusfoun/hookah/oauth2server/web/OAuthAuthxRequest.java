package com.jusfoun.hookah.oauth2server.web;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:21
 * @desc
 */
public class OAuthAuthxRequest extends OAuthAuthzRequest {


    public OAuthAuthxRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        super(request);
    }


    /*
    * 判断响应的类型是否为CODE
    * */
    public boolean isCode() {
        return ResponseType.CODE.name().equalsIgnoreCase(this.getResponseType());
    }

    /*
    * 判断响应的类型是否为TOKEN
    * */
    public boolean isToken() {
        return ResponseType.TOKEN.name().equalsIgnoreCase(this.getResponseType());
    }

    /*
    * 获取 request 对象
    * */
    public HttpServletRequest request() {
        return this.request;
    }
}
