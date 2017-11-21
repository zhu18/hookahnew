package com.jusfoun.hookah.oauth2server.security;

import com.jusfoun.hookah.core.Md5Utils;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.WxUserInfo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.UserService;
import com.jusfoun.hookah.rpc.api.WxUserInfoService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:21
 * @desc
 */
public class UsernameAndPasswordShiroRealm extends AuthorizingRealm {

    private static final Logger LOG = LoggerFactory.getLogger(UsernameAndPasswordShiroRealm.class);

    @Resource
    private UserService userService;

    @Resource
    private WxUserInfoService wxUserInfoService;

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOG.info("##################执行Shiro权限认证##################");
        String username = (String) super.getAvailablePrincipal(principalCollection);

        List<Condition> conditions = new ArrayList<>();
        conditions.add(Condition.eq("userName", username));
        User user = userService.selectOne(conditions);
        Session session = SecurityUtils.getSubject().getSession();
        if (user != null) {
            Map<String,String> userMap = new HashMap<String,String>();
            userMap.put("userId",user.getUserId());
            userMap.put("userName",user.getUserName());
            session.setAttribute("user",userMap);

            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //用户的角色集合
//      info.setRoles(user.getRolesName());
            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
//      List<Role> roleList = user.getRoleList();
//      for (Role role : roleList) {
//        info.addStringPermissions(role.getPermissionsName());
//      }
            // 或者按下面这样添加
            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
//            simpleAuthorInfo.addRole("admin");
            //添加权限
//            simpleAuthorInfo.addStringPermission("admin:manage");
//            logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //UsernamePasswordToken对象用来存放提交的登录信息
        UsernameAndPasswordToken token = (UsernameAndPasswordToken) authenticationToken;

        LOG.info("验证当前Subject时获取到token为：" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));

        if(token.getTokenType()== UsernameAndPasswordToken.TokenType.CLIENT) {
            return internalClientGetAuthenticationInfo(token);    //第三方登录认证
        } else {
            return internalUsernamePasswordGetAuthenticationInfo(token);//用户名密码登录认证
        }
    }

    /**
     * 用户名密码登录认证
     * @param token
     * @return
     */
    private  AuthenticationInfo internalUsernamePasswordGetAuthenticationInfo(final UsernameAndPasswordToken token){
        //查出是否有此用户
        List<Condition> conditions = new ArrayList<>();
        if(StringUtils.isNotBlank(token.getUsername())){
            conditions.add(Condition.eq("userName", token.getUsername()));
        }else if(StringUtils.isNotBlank(token.getMobile())){
            conditions.add(Condition.eq("mobile", token.getMobile()));
        }else{
            conditions.add(Condition.eq("email", token.getEmail()));
        }
        User user = userService.selectOne(conditions);

        if (user != null) {
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            AuthenticationInfo info = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
            if(info !=null){
                Session session = SecurityUtils.getSubject().getSession(true);
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("userId",user.getUserId());
                userMap.put("userName",user.getUserName());
                userMap.put("userType",user.getUserType());
                userMap.put("orgId",user.getOrgId());
                session.setAttribute("user",userMap);
            }
            return info;
        }
        return null;
    }

    private AuthenticationInfo internalClientGetAuthenticationInfo(final UsernameAndPasswordToken token) {
        List<Condition> conditions = new ArrayList<>();
        String openId = token.getUsername();
        conditions.add(Condition.eq("openid",openId));
        WxUserInfo wxUserInfo = wxUserInfoService.selectOne(conditions);
        if(Objects.nonNull(wxUserInfo)){
            String userId = wxUserInfo.getUserid();
            if(null != userId){
                User user = userService.selectById(userId);
                if (user != null) {
                    // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
                    AuthenticationInfo info = new SimpleAuthenticationInfo(openId,new Md5Hash(openId).toString(), getName());
                    if(info !=null){
                        Session session = SecurityUtils.getSubject().getSession(true);
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("userId",user.getUserId());
                        userMap.put("userName",user.getUserName());
                        userMap.put("userType",user.getUserType());
                        userMap.put("orgId",user.getOrgId());
                        session.setAttribute("user",userMap);
                    }
                    return info;
                }
            }
        }
        return null;
    }
}
