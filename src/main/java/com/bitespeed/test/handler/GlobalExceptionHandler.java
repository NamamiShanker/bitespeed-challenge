package com.bitespeed.test.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bitespeed.test.exception.ApirError;
import com.bitespeed.test.exception.BaseException;
import com.bitespeed.test.exception.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApirError> handleValidationException(ValidationException ve){
        LOGGER.error("Validation Error: ", ve);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        ApirError apiError = new ApirError(ve.getMessage(), ve.getInternalMessage(), timestamp, ve.getErrorCode());
        return ResponseEntity.status(ve.getErrorCode()).body(apiError);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApirError> handleBaseException(BaseException be){
        LOGGER.error("Internal Server Error", be);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        ApirError apiError = new ApirError(be.getMessage(), be.getInternalMessage(), timestamp, be.getErrorCode());
        return ResponseEntity.status(be.getErrorCode()).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e){
        LOGGER.error("An exception occured", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
