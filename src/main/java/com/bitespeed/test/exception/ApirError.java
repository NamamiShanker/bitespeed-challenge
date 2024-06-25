package com.bitespeed.test.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApirError {
    private String message;
    private String internalMessage;
    private String timestamp;
    private int statusCode;
}
