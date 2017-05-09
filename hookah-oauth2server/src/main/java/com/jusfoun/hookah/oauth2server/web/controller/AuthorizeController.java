package com.jusfoun.hookah.oauth2server.web.controller;


import com.jusfoun.hookah.core.domain.OauthClient;
import com.jusfoun.hookah.core.utils.FormatCheckUtil;
import com.jusfoun.hookah.oauth2server.security.UsernameAndPasswordToken;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthClientService;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthService;
import com.jusfoun.hookah.oauth2server.config.Constants;
import com.jusfoun.hookah.oauth2server.web.OAuthAuthxRequest;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:21
 * @desc
 */
@Controller
@RequestMapping("/oauth/")
public class AuthorizeController {


    private static final Logger logger = LoggerFactory.getLogger(AuthorizeController.class);

    @Resource
    private OAuthService oAuthService;

    @Resource
    private OAuthClientService clientService;

    @RequestMapping("authorize")
    public Object authorize(Model model, HttpServletRequest request, HttpServletResponse response) throws OAuthSystemException, URISyntaxException, ServletException, IOException {

        try {
            OAuthAuthxRequest oauthRequest = new OAuthAuthxRequest(request);
            //检查传入的客户端id是否正确
            if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
                OAuthResponse oAuthResponse = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                    .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                    .buildJSONMessage();
                return new ResponseEntity(oAuthResponse.getBody(), HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
            }

            Subject subject = SecurityUtils.getSubject();
            //如果用户没有登录，跳转到登陆页面 TODO...判断是否是POST
            if (!subject.isAuthenticated()) {
                if (!isLogin(subject, request)) {//登录失败时跳转到登陆页面
                    model.addAttribute("client", clientService.selectById(oauthRequest.getClientId()));
                    model.addAttribute("error", request.getAttribute("error"));
                    return "login";
                }
            }

            if (oauthRequest.isCode()) {
                String username = (String) subject.getPrincipal();
                //生成授权码
                String authCode = null;
                //responseType目前仅支持CODE，另外还有TOKEN
                OauthClient oauthClient = clientService.selectById(oauthRequest.getClientId());
                String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
                if (responseType.equals(ResponseType.CODE.toString())) {
                    OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                    authCode = oauthIssuerImpl.authorizationCode();
                    oAuthService.insertAuthCode(authCode, oauthClient);
                }

                //进行OAuth响应构建
                OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
                //设置授权码
                builder.setCode(authCode);
                //得到到客户端重定向地址
                String req_redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
                String backUrl = oauthRequest.getParam("backurl");
                //客户端注册时配置的返回地址，内部系统不需要
//                String redirectURI = oauthClient.getRedirectUri();
                if (!StringUtils.isEmpty(backUrl)) {
                    builder.setParam("backurl", backUrl);
                }
                builder.setParam("redirect_uri", req_redirectURI);
                //构建响应
                final OAuthResponse oAuthResponse = builder.location(oauthClient.getRedirectUri()).buildQueryMessage();

                //根据OAuthResponse返回ResponseEntity响应
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(new URI(oAuthResponse.getLocationUri()));
                return new ResponseEntity(headers, HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
            } else if (oauthRequest.isToken()) {

                return new ResponseEntity("ss", HttpStatus.NOT_FOUND);
            } else {
                OAuthResponse oAuthResponse = OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE)
                    .setErrorDescription("Unsupport response_type '" + oauthRequest.getResponseType() + "'")
                    .buildJSONMessage();
                return new ResponseEntity(oAuthResponse.getBody(), HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
            }

        } catch (OAuthProblemException e) {
            //出错处理
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                //告诉客户端没有传入redirectUri直接报错
                return new ResponseEntity("Unsupport response_type", HttpStatus.NOT_FOUND);
            }

            //返回错误消息（如?error=）
            final OAuthResponse oAuthResponse =
                OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                    .error(e).location(redirectUri).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(oAuthResponse.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
        }

    }

    @RequestMapping(value = "oauth_login")
    public String oauthLogin() {
        return "oauth/oauth_login";
    }


    @RequestMapping(value = "oauth_approval")
    public String oauthApproval() {
        return "oauth/oauth_approval";
    }


    private boolean isLogin(Subject subject, HttpServletRequest request) {
        if ("get".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String username = request.getParameter("userName");
        String password = request.getParameter("password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return false;
        }

        UsernameAndPasswordToken token = new UsernameAndPasswordToken();
        if(FormatCheckUtil.checkMobile(username)){
            token.setMobile(username);
        }else if(FormatCheckUtil.checkEmail(username)){
            token.setEmail(username);
        }else{
            token.setUsername(username);
        }
        token.setPassword(password.toCharArray());

        try {
            subject.login(token);
            return true;
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            request.setAttribute("error", "未知账户");
            return false;
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            request.setAttribute("error", "密码不正确");
            return false;
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            request.setAttribute("error", "账户已锁定");
            return false;
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            request.setAttribute("error", "用户名或密码错误次数过多");
            return false;
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            request.setAttribute("error", "用户名或密码不正确");
            return false;
        } catch (Exception e) {
            request.setAttribute("error", "登录失败:" + e.getClass().getName());
            return false;
        }
    }

}