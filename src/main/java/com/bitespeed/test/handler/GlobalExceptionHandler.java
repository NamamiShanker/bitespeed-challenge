package com.bitespeed.test.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bitespeed.test.exception.BaseException;
import com.bitespeed.test.exception.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationException> handleValidationException(ValidationException ve){
        LOGGER.error("Validation Error: ", ve);
        ve.setStackTrace(null);
        return ResponseEntity.status(ve.getErrorCode()).body(ve);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseException> handleBaseException(BaseException be){
        LOGGER.error("Internal Server Error", be);
        be.setStackTrace(null);
        return ResponseEntity.status(be.getErrorCode()).body(be);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e){
        LOGGER.error("An exception occured", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
