package com.bitespeed.test.enums;

@SuppressWarnings("unused")
public enum LinkPrecedence {
    
    SECONDARY("secondary"), PRIMARY("primary");

    private String precedence;

    LinkPrecedence(String precedence) {
        this.precedence = precedence;
    }

}
