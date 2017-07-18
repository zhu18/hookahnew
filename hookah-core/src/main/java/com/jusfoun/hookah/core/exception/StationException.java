package com.jusfoun.hookah.core.exception;

/**
 * Created by wangjl on 2017-3-15.
 */
public class StationException extends Exception {
    public StationException(String message) {
        super(message);
    }

    public StationException(Exception e) {
        super(e);
    }

    public StationException(String message, Exception e) {
        super(message,e);
    }
}
