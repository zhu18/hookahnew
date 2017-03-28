package com.jusfoun.hookah.oauth2server.web.controller;


import com.jusfoun.hookah.core.domain.OauthAccessToken;
import com.jusfoun.hookah.core.domain.OauthCode;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthAccessTokenService;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthCodeService;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthService;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:21
 * @desc
 */
@Controller
@RequestMapping("/oauth/")
public class OAuthAccessTokenController {

    @Autowired
    private OAuthService oAuthService;

    @Resource
    private OAuthCodeService oAuthCodeService;

    @Resource
    private OAuthAccessTokenService oAuthAccessTokenService;


    @RequestMapping("accessToken")
    public HttpEntity authorize(HttpServletRequest request) throws OAuthSystemException {
        try {
            //构建OAuth请求

            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

            //检查提交的客户端id是否正确
//            if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
//                OAuthResponse response =
//                    OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
//                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
//                        .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
//                        .buildJSONMessage();
//                return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
//            }

            // 检查客户端安全KEY是否正确
//            if (!oAuthService.checkClientSecret(oauthRequest.getClientSecret())) {
//                OAuthResponse response =
//                    OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
//                        .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
//                        .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
//                        .buildJSONMessage();
//                return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
//            }

            String code = oauthRequest.getParam(OAuth.OAUTH_CODE);
            OauthCode oauthCode = oAuthCodeService.selectById(code);
            // 检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD或REFRESH_TOKEN
            if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
                if (oauthCode == null) {
                    OAuthResponse response = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_GRANT)
                        .setErrorDescription("错误的授权码")
                        .buildJSONMessage();
                    return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
                }
            }

            //生成Access Token
            OauthAccessToken oAuthAccessToken = oAuthAccessTokenService.insertAccessToken(oauthCode);

            //生成OAuth响应
            OAuthResponse response = OAuthASResponse
                .tokenResponse(HttpServletResponse.SC_OK)
                .setAccessToken(oAuthAccessToken.getTokenId())
                .setRefreshToken(oAuthAccessToken.getRefreshToken())
                .setTokenType(oAuthAccessToken.getTokenType())
                .setExpiresIn(String.valueOf(oAuthAccessToken.getTokenExpiredSeconds()))
                .buildJSONMessage();

            //根据OAuthResponse生成ResponseEntity
            return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));

        } catch (OAuthProblemException e) {
            //构建错误响应
            OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
                .buildJSONMessage();
            return new ResponseEntity(res.getBody(), HttpStatus.valueOf(res.getResponseStatus()));
        }

    }
}
