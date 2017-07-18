package com.jusfoun.hookah.core.exception;

/**
 * Created by wangjl on 2017-3-15.
 */
public class EmailException extends Exception {
    public EmailException(String message) {
        super(message);
    }

    public EmailException(Exception e) {
        super(e);
    }

    public EmailException(String message, Exception e) {
        super(message,e);
    }
}
