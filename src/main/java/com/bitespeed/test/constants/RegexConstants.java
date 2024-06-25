package com.bitespeed.test.constants;

import java.util.regex.Pattern;

public class RegexConstants {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PHONE_REGEX = "^[0-9]{10}$";

    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
}
