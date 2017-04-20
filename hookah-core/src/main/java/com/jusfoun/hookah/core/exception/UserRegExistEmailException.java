package com.jusfoun.hookah.core.exception;

/**
 * Created by jsshao on 2017-3-15.
 */
public class UserRegExistEmailException extends Exception {
    public UserRegExistEmailException(String message) {
        super(message);
    }

    public UserRegExistEmailException(Exception e) {
        super(e);
    }

    public UserRegExistEmailException(String message, Exception e) {
        super(message,e);
    }
}
