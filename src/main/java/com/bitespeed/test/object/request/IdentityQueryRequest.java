package com.bitespeed.test.object.request;

import org.springframework.util.StringUtils;

import com.bitespeed.test.constants.IdentityConstants;
import com.bitespeed.test.exception.ValidationException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class IdentityQueryRequest {
    private String email;
    private String phoneNumber;

    public void validate() throws ValidationException{
        if(!StringUtils.hasText(email) && !StringUtils.hasText(phoneNumber))
            throw new ValidationException(IdentityConstants.PHONE_AND_EMAIL_IS_NULL, IdentityConstants.PHONE_AND_EMAIL_IS_NULL);
    }
}
