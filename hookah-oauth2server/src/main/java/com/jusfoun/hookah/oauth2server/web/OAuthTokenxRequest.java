package com.jusfoun.hookah.oauth2server.web;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:21
 * @desc
 */
public class OAuthTokenxRequest extends OAuthTokenRequest {

  /**
   * Create an OAuth Token request from a given HttpSerlvetRequest
   *
   * @param request the httpservletrequest that is validated and transformed into the OAuth Token Request
   * @throws OAuthSystemException  if an unexpected exception was thrown
   * @throws OAuthProblemException if the request was not a valid Token request this exception is thrown.
   */
  public OAuthTokenxRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
    super(request);
  }

  public HttpServletRequest request() {
    return this.request;
  }
}
