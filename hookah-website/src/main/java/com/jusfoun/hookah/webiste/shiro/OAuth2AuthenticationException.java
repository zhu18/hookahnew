package com.jusfoun.hookah.webiste.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author huang lei
 * @date 2017/3/27 下午15:26
 * @desc
 */
public class OAuth2AuthenticationException extends AuthenticationException {

    public OAuth2AuthenticationException(Throwable cause) {
        super(cause);
    }
}
