package com.jusfoun.hookah.console.server.shiro;


import com.alibaba.fastjson.JSONObject;
import com.jusfoun.hookah.rpc.api.RoleService;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author huang lei
 * @date 2017/3/27 下午15:26
 * @desc
 */
public class OAuth2Realm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2Realm.class);

    @Resource
    private RoleService roleService;

    private String clientId;
    private String clientSecret;
    private String accessTokenUrl;
    private String userInfoUrl;
    private String redirectUrl;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;//表示此Realm只支持OAuth2Token类型
    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new OAuth2AuthenticationException("PrincipalCollection method argument cannot be null.");
        }
        Session session = SecurityUtils.getSubject().getSession();
        Map<String,String> user = (Map<String,String>)session.getAttribute("user");
        Set<String> roleNames = roleService.selectRolesByUserId(user.get("userId"));
//        String username = (String) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roleNames);
        return authorizationInfo;
    }

    /**
     * 登录认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OAuth2Token oAuth2Token = (OAuth2Token) token;
        String code = oAuth2Token.getAuthCode();
        Map info = extractUser(code);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(info, code, getName());
        return authenticationInfo;
    }

    private Map extractUser(String code) {

        try {
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                .tokenLocation(accessTokenUrl)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setCode(code)
                .setRedirectURI(redirectUrl)
                .buildQueryMessage();

            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);

            String accessToken = oAuthResponse.getAccessToken();
            Long expiresIn = oAuthResponse.getExpiresIn();

            OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(userInfoUrl)
                .setAccessToken(accessToken).buildQueryMessage();

            OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            String userJson = resourceResponse.getBody();
            JSONObject jsonObject = JSONObject.parseObject(userJson);
            Map<String,String> user = new HashMap<String,String>();
            user.put("userId",jsonObject.getString("userId"));
            user.put("userName",jsonObject.getString("userName"));
//            SecurityUtils.getSubject().getSession().setAttribute("user",user);
            return user;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new OAuth2AuthenticationException(e);
        }
    }
}
