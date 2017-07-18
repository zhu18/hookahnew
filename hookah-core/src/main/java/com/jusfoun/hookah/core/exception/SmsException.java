package com.jusfoun.hookah.core.exception;

/**
 * Created by wangjl on 2017-3-15.
 */
public class SmsException extends Exception {
    public SmsException(String message) {
        super(message);
    }

    public SmsException(Exception e) {
        super(e);
    }

    public SmsException(String message, Exception e) {
        super(message,e);
    }
}
