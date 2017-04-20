package com.jusfoun.hookah.core.exception;

/**
 * Created by jsshao on 2017-3-15.
 */
public class UserRegExistMobileException extends Exception {
    public UserRegExistMobileException(String message) {
        super(message);
    }

    public UserRegExistMobileException(Exception e) {
        super(e);
    }

    public UserRegExistMobileException(String message, Exception e) {
        super(message,e);
    }
}
