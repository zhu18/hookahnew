package com.jusfoun.hookah.core.exception;

/**
 * Created by jsshao on 2017-3-15.
 */
public class UserRegConfirmPwdException extends Exception {
    public UserRegConfirmPwdException(String message) {
        super(message);
    }

    public UserRegConfirmPwdException(Exception e) {
        super(e);
    }

    public UserRegConfirmPwdException(String message, Exception e) {
        super(message,e);
    }
}
