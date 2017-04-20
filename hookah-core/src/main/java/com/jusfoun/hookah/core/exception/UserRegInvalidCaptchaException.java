package com.jusfoun.hookah.core.exception;

/**
 * Created by jsshao on 2017-3-15.
 */
public class UserRegInvalidCaptchaException extends Exception {
    public UserRegInvalidCaptchaException(String message) {
        super(message);
    }

    public UserRegInvalidCaptchaException(Exception e) {
        super(e);
    }

    public UserRegInvalidCaptchaException(String message, Exception e) {
        super(message,e);
    }
}
