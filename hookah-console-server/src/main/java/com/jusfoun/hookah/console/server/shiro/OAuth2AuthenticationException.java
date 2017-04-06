package com.jusfoun.hookah.console.server.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author huang lei
 * @date 2017/3/27 下午15:26
 * @desc
 */
public class OAuth2AuthenticationException extends AuthenticationException {

    public OAuth2AuthenticationException() {
    }

    public OAuth2AuthenticationException(String message) {
        super(message);
    }

    public OAuth2AuthenticationException(Throwable cause) {
        super(cause);
    }

    public OAuth2AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
