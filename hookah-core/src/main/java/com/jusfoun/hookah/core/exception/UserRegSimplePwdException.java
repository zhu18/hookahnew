package com.jusfoun.hookah.core.exception;

/**
 * Created by jsshao on 2017-3-15.
 */
public class UserRegSimplePwdException extends Exception {
    public UserRegSimplePwdException(String message) {
        super(message);
    }

    public UserRegSimplePwdException(Exception e) {
        super(e);
    }

    public UserRegSimplePwdException(String message, Exception e) {
        super(message,e);
    }
}
