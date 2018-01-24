package com.jusfoun.hookah.oauth2server.security;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ctp on 2018/1/18.
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    RedisOperate redisOperate;

    @Autowired
    UserService userService;

    private static final Integer RETRY_DURATION_MINITE = 5;//有效时间

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {

        //UsernamePasswordToken对象用来存放提交的登录信息
        UsernameAndPasswordToken authenticationToken = (UsernameAndPasswordToken) token;

        //联合登录除外
        if(authenticationToken.getTokenType()== UsernameAndPasswordToken.TokenType.CLIENT) {
            return  super.doCredentialsMatch(token, info);//第三方登录认证
        } else {

            //查出是否有此用户
            List<Condition> conditions = new ArrayList<>();
            if(StringUtils.isNotBlank(authenticationToken.getUsername())){
                conditions.add(Condition.eq("userName", authenticationToken.getUsername()));
            }else if(StringUtils.isNotBlank(authenticationToken.getMobile())){
                conditions.add(Condition.eq("mobile", authenticationToken.getMobile()));
            }else{
                conditions.add(Condition.eq("email", authenticationToken.getEmail()));
            }
            User user = userService.selectOne(conditions);

            if(user == null){
                return  super.doCredentialsMatch(token, info);
            }

//            String username = (String) token.getPrincipal();
            //用户名
            String username = user.getUserName();
            //验证码
//            String picValid = authenticationToken.getPicValid();

            // retry count + 1
            AtomicInteger retryCount = (AtomicInteger) redisOperate.getObject("retry:" + username);
            if (retryCount == null) {
                retryCount = new AtomicInteger(1);
                redisOperate.setObject("retry:" + username, retryCount,RETRY_DURATION_MINITE * 60);
            } else {
                redisOperate.setObject("retry:" + username, new AtomicInteger(retryCount.incrementAndGet()),RETRY_DURATION_MINITE * 60);
            }

            //5分钟内超过5次锁定用户
            if (retryCount.get() > 5) {
                // if retry count > 5 throw
                throw new LockedAccountException();
            }

            boolean matches = super.doCredentialsMatch(token, info);
            if (matches) {
                // clear retry count
                redisOperate.delObject("retry:" + username);
            } else {
                //校验验证码
                if(retryCount.get() >= 3){
                    throw new ExcessiveAttemptsException();
                }
            }
            return matches;
        }

    }



}
