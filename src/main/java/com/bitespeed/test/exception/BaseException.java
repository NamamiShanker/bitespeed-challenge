package com.bitespeed.test.exception;

import lombok.Getter;

@Getter
public class BaseException extends Exception {
    
    private int                     errorCode;
    private String                  internalMessage;

    /**
     * @param errorCode
     * @param message
     * @param internalMessage
     */
    public BaseException(int errorCode, String message, String internalMessage) {
        super(message);
        this.errorCode = errorCode;
        this.internalMessage = internalMessage;
    }

}
