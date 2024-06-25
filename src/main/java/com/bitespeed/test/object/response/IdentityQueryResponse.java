package com.bitespeed.test.object.response;

import java.util.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentityQueryResponse {

    public IdentityResponse contact;
    
    @Getter
    @Setter
    public static class IdentityResponse {
        public int primaryContactId;
        public List<String> emails;
        public List<String> phoneNumbers;
        public List<Integer> secondaryContactIds;
    }
}
