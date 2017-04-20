package com.jusfoun.hookah.core.exception;

/**
 * Created by jsshao on 2017-3-15.
 */
public class UserRegExistUsernameException extends Exception {
    public UserRegExistUsernameException(String message) {
        super(message);
    }

    public UserRegExistUsernameException(Exception e) {
        super(e);
    }

    public UserRegExistUsernameException(String message, Exception e) {
        super(message,e);
    }
}
