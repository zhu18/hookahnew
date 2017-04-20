package com.jusfoun.hookah.core.exception;

/**
 * Created by jsshao on 2017-3-15.
 */
public class UserRegInvalidSmsException extends Exception {
    public UserRegInvalidSmsException(String message) {
        super(message);
    }

    public UserRegInvalidSmsException(Exception e) {
        super(e);
    }

    public UserRegInvalidSmsException(String message, Exception e) {
        super(message,e);
    }
}
