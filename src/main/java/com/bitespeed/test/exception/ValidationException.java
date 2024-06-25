package com.bitespeed.test.exception;

import com.bitespeed.test.constants.Common;

import lombok.Getter;

@Getter
public class ValidationException extends BaseException{

    public ValidationException(String message, String internalMessage) {
        super(400, message, Common.BAD_REQUEST+internalMessage);
    }
    
}
