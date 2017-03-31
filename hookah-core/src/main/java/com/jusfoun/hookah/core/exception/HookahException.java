package com.jusfoun.hookah.core.exception;

/**
 * Created by wangjl on 2017-3-15.
 */
public class HookahException extends RuntimeException {
    public HookahException(String message) {
        super(message);
    }

    public HookahException(Exception e) {
        super(e);
    }

    public HookahException(String message,Exception e) {
        super(message,e);
    }
}
