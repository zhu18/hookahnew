package com.jusfoun.hookah.core.exception;

/**
 * Created by jsshao on 2017-3-15.
 */
public class UserRegExpiredSmsException extends Exception {
    public UserRegExpiredSmsException(String message) {
        super(message);
    }

    public UserRegExpiredSmsException(Exception e) {
        super(e);
    }

    public UserRegExpiredSmsException(String message, Exception e) {
        super(message,e);
    }
}
