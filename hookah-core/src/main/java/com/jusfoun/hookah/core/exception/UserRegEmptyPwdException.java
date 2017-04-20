package com.jusfoun.hookah.core.exception;

/**
 * Created by jsshao on 2017-3-15.
 */
public class UserRegEmptyPwdException extends Exception {
    public UserRegEmptyPwdException(String message) {
        super(message);
    }

    public UserRegEmptyPwdException(Exception e) {
        super(e);
    }

    public UserRegEmptyPwdException(String message, Exception e) {
        super(message,e);
    }
}
